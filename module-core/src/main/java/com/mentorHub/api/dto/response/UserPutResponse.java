package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPutResponse {
    private Long userId;

    private String name;

    public static UserPutResponse from(UserEntity en) {
        return UserPutResponse.builder()
                .userId(en.getUserId())
                .name(en.getName())
                .build();
    }
}
