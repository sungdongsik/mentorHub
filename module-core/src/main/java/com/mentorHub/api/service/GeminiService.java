package com.mentorHub.api.service;

import com.mentorHub.api.dto.IntentResponse;
import com.mentorHub.api.message.ChatDefaultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ChatClient chatClient;

    public IntentResponse classify(String message) {

        String prompt = ChatDefaultMessage.INTENT_CLASSIFIER.format(message);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .entity(IntentResponse.class);
    }

}
