package com.mentorHub.api.service;

import com.mentorHub.api.dto.IntentResponse;
import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.dto.response.MenteeKeywordResponse;
import com.mentorHub.api.entity.MenteeEntity;
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
        IntentResponse response = geminiService.classify(request.getContent());

        if (response.isMenteeSearch()) {
            List<String> keywords = response.getKeywords();

            List<MenteeEntity> mentees = menteeService.findByKeywords(keywords);

            return ChatMessageResponse.from(response.getAnswer(), MenteeKeywordResponse.fromList(mentees));
        }

        return ChatMessageResponse.from(response.getAnswer(), null);
    }
}
