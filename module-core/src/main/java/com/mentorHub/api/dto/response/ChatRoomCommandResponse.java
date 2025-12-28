package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ChatRoomEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomCommandResponse {
    private Long chatId;

    private String title;

    private LocalDateTime createDate;

    public static ChatRoomCommandResponse from(ChatRoomEntity en) {
        return ChatRoomCommandResponse.builder()
                .chatId(en.getChatId())
                .title(en.getTitle())
                .createDate(en.getCreatedDate())
                .build();
    }
}
