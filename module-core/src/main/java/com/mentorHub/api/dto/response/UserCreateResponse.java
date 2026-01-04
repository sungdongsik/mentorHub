package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateResponse {
    private Long userId;

    private String name;

    public static UserCreateResponse from(UserEntity en) {
        return UserCreateResponse.builder()
                .userId(en.getUserId())
                .name(en.getName())
                .build();
    }
}
