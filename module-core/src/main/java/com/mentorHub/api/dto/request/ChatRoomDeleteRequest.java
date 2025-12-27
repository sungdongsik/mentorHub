package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.ChatRoomEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomDeleteRequest {
    private Long chatId;

    public ChatRoomEntity toEntity() {
        return ChatRoomEntity.builder()
                .chatId(chatId)
                .build();
    }
}
