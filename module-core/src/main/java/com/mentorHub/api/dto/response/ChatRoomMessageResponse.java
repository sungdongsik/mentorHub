package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ChatRoomMessageEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatRoomMessageResponse {
    private Long chatId;

    private List<ChatRoomMessageEntity> chatRoomMessages;

}
