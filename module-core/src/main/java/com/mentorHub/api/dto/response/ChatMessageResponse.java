package com.mentorHub.api.dto.response;

import com.util.ChatRoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {
    private ChatRoleType role;

    private LocalDateTime createDate;

    private String message;

    public static ChatMessageResponse from(String message) {
        return ChatMessageResponse.builder()
                .role(ChatRoleType.BOT)
                .createDate(LocalDateTime.now())
                .message(message)
                .build();
    }
}
