package com.mentorHub.api.dto.response;

import com.util.ChatRoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ChatMessageResponse {
    private ChatRoleType role;

    private List<MenteeKeywordResponse> contents;

    private LocalDateTime createDate;

    private String message;

    public static ChatMessageResponse from(String content, List<MenteeKeywordResponse> contents) {
        return ChatMessageResponse.builder()
                .role(ChatRoleType.BOT)
                .contents(contents)
                .createDate(LocalDateTime.now())
                .message(content)
                .build();
    }
}
