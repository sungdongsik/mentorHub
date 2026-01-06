package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.message.ChatDefaultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ChatClient chatClient;

    public String classify(List<MenteeEntity> mentees, String message) {

        String prompt = ChatDefaultMessage.INTENT_CLASSIFIER.format(mentees, message);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

}
