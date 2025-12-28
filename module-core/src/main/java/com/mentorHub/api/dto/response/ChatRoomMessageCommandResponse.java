package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ChatRoomEntity;
import com.mentorHub.api.entity.ChatRoomMessageEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatRoomMessageCommandResponse {
    private Long chatId;

    private List<ChatRoomMessageResponse> chatRoomMessages;

    public static ChatRoomMessageCommandResponse from(List<ChatRoomMessageEntity> chatRoomMessages, ChatRoomEntity en) {

        return ChatRoomMessageCommandResponse.builder()
                .chatId(en.getChatId())
                .chatRoomMessages(chatRoomMessages.stream()
                        .map(e -> ChatRoomMessageResponse.builder()
                        .messageId(e.getMessageId())
                        .role(e.getRole())
                        .content(e.getContent())
                        .createDate(e.getCreatedDate())
                        .build())
                        .toList())
                .build();

    }
}
