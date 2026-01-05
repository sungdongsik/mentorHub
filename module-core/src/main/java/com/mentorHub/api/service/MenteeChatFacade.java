package com.mentorHub.api.service;

import com.mentorHub.api.dto.IntentResponse;
import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.formatter.MessageFormatter;
import com.message.ChatDefaultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenteeChatFacade {
    private final MenteeService menteeService;

    private final GeminiService geminiService;

    public ChatMessageResponse sendMessage(ChatMessageCreateRequest request) {
        // 사용자가 입력한 메시지를 분석하여 멘티 추천 관련 질문인지, 일반 대화인지 분류한다.
        IntentResponse intent = geminiService.classify(request.getContent());

        // 멘티 추천 의도일 경우
        if (intent.isMenteeSearch()) {
            return menteeSearch(intent);
        }

        // CHAT일 경우 제미나이 응답으로 리턴하기
        return ChatMessageResponse.from(geminiService.geminiChat(request.getContent()));
    }

    private ChatMessageResponse menteeSearch(IntentResponse intent) {

        if (intent.getSkills().isEmpty()) {
            return ChatMessageResponse.from(ChatDefaultMessage.NO_SKILL_DETECTED.getMessage());
        }

        List<MenteeEntity> mentees = menteeService.findByKeywords(intent.getSkills());

        if (mentees.isEmpty()) {
            return ChatMessageResponse.from(ChatDefaultMessage.NO_MENTEE_FOUND.getMessage());
        }

        return ChatMessageResponse.from(MessageFormatter.toChatMessage(mentees));
    }
}
