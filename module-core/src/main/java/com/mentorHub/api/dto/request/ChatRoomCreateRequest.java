package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.ChatRoomEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomCreateRequest {
    private Long userId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    public ChatRoomEntity toEntity() {
        return ChatRoomEntity.builder()
                .userId(userId)
                .title(title)
                .build();
    }
}
