package com.mentorHub.api.dto.response;

import com.util.ChatRoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomMessageResponse {
    private Long messageId;

    private ChatRoleType role;

    private String content;

    private LocalDateTime createDate;
}
