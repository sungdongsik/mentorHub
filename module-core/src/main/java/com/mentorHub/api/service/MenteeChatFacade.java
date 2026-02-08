package com.mentorHub.api.service;

import com.mentorHub.api.dto.ChatResponse;
import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.dto.response.MenteeSummaryResponse;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import com.mentorHub.api.vector.VectorService;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenteeChatFacade {

    private final GeminiService geminiService;

    private final VectorService vectorService;

    private final MenteeService menteeService;

    public ChatMessageResponse sendMessage(ChatMessageCreateRequest request) {
        // 사용자가 입력한 정보로 가장 비슷한 멘티 10개 가져오기
        List<Document> documents = vectorService.searchSimilarMentees(request.getContent(), 10);

        // 사용자가 입력한 메시지를 분석
        ChatResponse response = geminiService.classify(documents, request.getContent());

        // MENTEE_SEARCH일 경우 로직 태우기
        if (response.isMenteeSearch()) {
            List<MenteeEntity> mentees = menteeService.findByWritingIdsAndActiveKeywords(response.getWritingIds());

            // 로직 나중에 수정하자!! 퍼사드가 너무 많은 처리를 하는 거 같음..
            List<MenteeSummaryResponse> summaries = mentees.stream()
                    .map(m -> new MenteeSummaryResponse(
                            m.getName(),
                            m.getKeywords().stream()
                                    .filter(k -> k.getRootKeyword().getStatus() == RootKeywordAliasStatus.ACTIVE)
                                    .map(MenteeKeywordEntity::getKeyword)
                                    .distinct()
                                    .toList()
                    ))
                    .toList();

            return ChatMessageResponse.from(response, summaries);
        }

        return ChatMessageResponse.from(response);
    }
}
