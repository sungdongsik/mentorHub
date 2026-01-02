package com.mentorHub.api.dto.response;

import com.util.ChatRoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {
    private ChatRoleType role;

    private String content;

    private LocalDateTime createDate;

    public static ChatMessageResponse from(String content) {
        return ChatMessageResponse.builder()
                .role(ChatRoleType.BOT)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
    }
}
