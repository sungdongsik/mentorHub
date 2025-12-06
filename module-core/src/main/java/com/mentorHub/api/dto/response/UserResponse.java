package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long userId;

    private String name;

    public static UserResponse from(UserEntity en) {
        return UserResponse.builder()
                .userId(en.getUserId())
                .name(en.getName())
                .build();
    }
}
