package com.mentorHub.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageCreateRequest {
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
