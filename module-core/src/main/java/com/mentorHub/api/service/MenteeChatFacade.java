package com.mentorHub.api.service;

import com.mentorHub.api.dto.IntentResponse;
import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.entity.MenteeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MenteeChatFacade {
    private final MenteeService menteeService;

    private final GeminiService geminiService;

    public ChatMessageResponse sendMessage(ChatMessageCreateRequest request) {
        // 사용자가 입력한 메시지를 분석하여 멘티 추천 관련 질문인지, 일반 대화인지 분류한다.
        IntentResponse intent = geminiService.classify(request.getContent());

        // 멘티 추천 의도일 경우
        if(intent.isMenteeSearch()) {
            List<MenteeEntity> keywords = menteeService.findByKeywords(intent.getSkills());

            String message = keywords.stream()
                    .map(m -> m.getName() + ", " + m.getKeyword() + " 입니다")
                    .collect(Collectors.joining("\n"));

            // 우리 DB에 있으면 그걸로 답변
            if(!keywords.isEmpty()) {
                return ChatMessageResponse.from(message);
            }

        }
        // CHAT일 경우 제미나이 응답으로 리턴하기
        return ChatMessageResponse.from(geminiService.geminiChat(request.getContent()));
    }
}
