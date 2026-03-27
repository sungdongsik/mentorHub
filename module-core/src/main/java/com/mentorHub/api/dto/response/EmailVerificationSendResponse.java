package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.EmailVerificationEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailVerificationSendResponse {
    private Long verificationId;

    private String email;

    public static EmailVerificationSendResponse from(EmailVerificationEntity en) {
        return EmailVerificationSendResponse.builder()
                .verificationId(en.getVerificationId())
                .email(en.getEmail())
                .build();
    }
}
