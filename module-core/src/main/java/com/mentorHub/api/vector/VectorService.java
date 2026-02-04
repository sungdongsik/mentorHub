package com.mentorHub.api.vector;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorService {

    private final VectorStore vectorStore;

    /**
     * Vector Store에 저장
     */
    public void saveDocument(Document document) {
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
