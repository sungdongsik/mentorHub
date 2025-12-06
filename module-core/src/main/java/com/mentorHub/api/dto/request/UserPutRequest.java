package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.UserEntity;
import com.util.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPutRequest {
    private Long userId;

    private String name;

    private String phoneNumber;

    private UserType status;

    private String keyword[];

    private String job;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .name(name)
                .phoneNumber(phoneNumber)
                .keyword(keyword)
                .job(job)
                .build();
    }
}
