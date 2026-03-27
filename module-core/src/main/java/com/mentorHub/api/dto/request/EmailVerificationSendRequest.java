package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.EmailVerificationEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailVerificationSendRequest {
    @Email
    @NotBlank
    private String email;

    public EmailVerificationEntity toEntity() {
        return EmailVerificationEntity.builder()
                .email(email)
                .build();
    }
}
