package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.util.ApplicationStausType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 25.11.29
 * 멘티 신청하기 Request Dto
 * 주요 포함 정보:
 * - userId: 사용자 고유 ID
 * - name: 이름
 * - startDate: 시작일
 * - comment: 메모
 */
@Getter
@ToString
public class MenteeApplicationCreateRequest {
    private Long userId;

    private String name;

    private LocalDateTime startDate;

    private String comment;

    private ApplicationStausType admission;

    public MenteeApplicationEntity toEntity() {
        return MenteeApplicationEntity.builder()
                .userId(userId)
                .name(name)
                .startDate(startDate)
                .comment(comment)
                .admission(admission)
                .build();
    }
}
