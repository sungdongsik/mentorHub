package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.UserEntity;
import com.util.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateRequest {
    private String email;

    private String pass;

    private String name;

    private String phoneNumber;

    private UserType status;

    private String[] keyword;

    private String job;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .pass(pass)
                .name(name)
                .phoneNumber(phoneNumber)
                .status(status)
                .keyword(keyword)
                .job(job)
                .build();
    }
}
