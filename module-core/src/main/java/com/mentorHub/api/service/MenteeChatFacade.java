package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MenteeChatFacade {
    private final MenteeService menteeService;

    private final GeminiService geminiService;

    public ChatMessageResponse sendMessage(ChatMessageCreateRequest request) {
        // 사용자가 입력한 메시지를 분석하여 멘티 추천 관련 질문인지, 일반 대화인지 분류한다.
        String result = geminiService.classify(menteeService.findAll(), request.getContent());

        return ChatMessageResponse.from(result);
    }
}
