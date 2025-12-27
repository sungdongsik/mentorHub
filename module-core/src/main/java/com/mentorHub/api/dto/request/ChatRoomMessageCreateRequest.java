package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.ChatRoomEntity;
import com.mentorHub.api.entity.ChatRoomMessageEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomMessageCreateRequest {
    private Long chatId;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public ChatRoomMessageEntity toEntity(ChatRoomEntity en) {
        return ChatRoomMessageEntity.builder()
                .chatRoom(en)
                .content(content)
                .build();
    }
}
