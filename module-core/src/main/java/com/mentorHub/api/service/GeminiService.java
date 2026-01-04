package com.mentorHub.api.service;

import com.mentorHub.api.dto.IntentResponse;
import com.message.ChatDefaultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ChatClient chatClient;

    public String geminiChat(String userMessage) {

        return chatClient
                .prompt()
                .system(ChatDefaultMessage.MENTEE_RECOMMENDATION.getMessage())
                .user(userMessage)
                .call()
                .content();
    }

    public IntentResponse classify(String message) {

        String prompt = ChatDefaultMessage.INTENT_CLASSIFIER.format(message);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .entity(IntentResponse.class);
    }

}
