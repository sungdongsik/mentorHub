package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.EmailVerificationEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailVerificationVerifyResponse {
    private Long verificationId;

    private String email;

    public static EmailVerificationVerifyResponse from(EmailVerificationEntity en) {
        return EmailVerificationVerifyResponse.builder()
                .verificationId(en.getVerificationId())
                .email(en.getEmail())
                .build();
    }
}
