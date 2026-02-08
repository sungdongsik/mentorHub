package com.mentorHub.api.dto.response;

import com.mentorHub.api.dto.ChatResponse;
import com.util.ChatRoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ChatMessageResponse {
    private ChatRoleType role;

    private LocalDateTime createDate;

    private List<MenteeSummaryResponse> mentees;

    private String message;

    public static ChatMessageResponse from(ChatResponse response, List<MenteeSummaryResponse> skills) {
        return ChatMessageResponse.builder()
                .role(response.getType())
                .createDate(LocalDateTime.now())
                .mentees(skills)
                .message(response.getMessage())
                .build();
    }

    public static ChatMessageResponse from(ChatResponse response) {
        return ChatMessageResponse.builder()
                .role(response.getType())
                .createDate(LocalDateTime.now())
                .message(response.getMessage())
                .build();
    }
}
