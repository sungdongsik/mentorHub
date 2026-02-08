package com.mentorHub.api.service;

import com.mentorHub.api.dto.ChatResponse;
import com.mentorHub.api.message.ChatDefaultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ChatClient chatClient;

    public ChatResponse classify(List<Document> documents, String message) {

        String prompt = ChatDefaultMessage.INTENT_CLASSIFIER.format(documents, message);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .entity(ChatResponse.class);
    }

}
