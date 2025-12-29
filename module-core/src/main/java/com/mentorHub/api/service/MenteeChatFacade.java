package com.mentorHub.api.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.mentorHub.api.entity.ChatRoomMessageEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.message.ChatDefaultMessage;
import com.util.ChatRoleType;
import com.util.ChatSelectMessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenteeChatFacade {
    private final ChatRoomService chatRoomService;

    private final MenteeService menteeService;

    private final Client client;

    @Transactional
    public List<ChatRoomMessageEntity> sendMessage(ChatRoomMessageEntity request) {

        // 1. 사용자가 입력한 메시지 저장
        ChatRoomMessageEntity userMessage = chatRoomService.setMessage(request);

        // 2. 사용자 입력을 기반으로 BOT 응답 생성
        String botContent = createBotResponse(request.getContent());

        // 3. BOT 메시지 생성
        ChatRoomMessageEntity botMessage = ChatRoomMessageEntity.builder()
                .chatRoom(request.getChatRoom())
                .role(ChatRoleType.BOT)
                .content(botContent)
                .build();

        // 4. BOT 메시지 저장
        ChatRoomMessageEntity savedBotMessage = chatRoomService.setMessage(botMessage);

        // 5. 사용자 메시지 + BOT 메시지 반환
        return List.of(userMessage, savedBotMessage);
    }

    // 사용자 입력을 기준으로 BOT 응답 메시지 생성
    private String createBotResponse(String content) {

        // 입력된 키워드로 멘티 조회
        List<MenteeEntity> mentees = menteeService.getChatMentee(content);

        // 멘티가 존재하면 추천 메시지 반환
        if (!mentees.isEmpty()) {
            return buildMenteeMessage(mentees);
        }

        // 멘티가 없으면 기본 안내 메시지 반환
        return createBotMessage(content);
    }

    // 멘티가 없을 경우 선택 유형에 따른 기본 BOT 메시지 생성
    private String createBotMessage(String content) {

        ChatSelectMessageType selectType = ChatSelectMessageType.from(content);

        // 선택 유형이 없으면 기본 안내 메시지 반환
        return selectType == null ? ChatDefaultMessage.messageSelect() : selectType.getMessage();
    }

    // 멘티 추천 결과 메시지 생성
    public String buildMenteeMessage(List<MenteeEntity> mentees) {

        if (mentees.isEmpty()) {
            return ChatDefaultMessage.messageSelect();
        }

        // 멘티 목록을 문자열 메시지로 변환
        return "추천 멘티를 소개할게요 😊\n\n" +
                mentees.stream()
                        .map(m -> "• " + m.getName()
                                + " (" + String.join(", ", m.getKeyword()) + ")")
                        .collect(Collectors.joining("\n"));
    }


    public String generate(String prompt) {
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        return restClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
                .header("x-goog-api-key", apiKey)
                .body(body)
                .retrieve()
                .body(String.class);
    }
}
