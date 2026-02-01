package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VectorService {

    private final VectorStore vectorStore;

    /**
     * 멘티 정보를 Vector Store에 저장
     */
    public void saveMenteeDocument(MenteeEntity en) {

        // 승인된(ACTIVE) 키워드들만 필터링해서 문자열로 합침
        String approvedKeywords = en.getKeywords().stream()
                .filter(mk -> RootKeywordAliasStatus.ACTIVE.equals(mk.getRootKeyword().getStatus())) // RootKeyword 상태 체크
                .map(MenteeKeywordEntity::getKeyword)
                .collect(Collectors.joining(", "));

        Document document = new Document(
                en.getWritingId().toString(), // ID
                en.getName() + "," + approvedKeywords,             // 벡터화할 텍스트 내용
                Map.of(
                        "writing_id", en.getWritingId()
                )
        );

        vectorStore.add(List.of(document));
    }

    /**
     * 유사한 멘티 정보 조회 (RAG용)
     */
    public List<Document> searchSimilarMentees(String query, int topK) {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
        );
    }
}
