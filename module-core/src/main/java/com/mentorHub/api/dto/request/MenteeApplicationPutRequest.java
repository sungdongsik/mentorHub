package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.util.ApplicationStausType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * 25.11.29
 * 멘티 신청 승인하기 Request Dto
 * 주요 포함 정보:
 * - menteeId: 멘티 신청 고유 ID
 * - admission: 승인 상태
 */

@Getter
@ToString
public class MenteeApplicationPutRequest {
    @NotNull(message = "멘티 글 ID는 필수입니다.")
    private Long menteeId;

    private ApplicationStausType admission;


    public MenteeApplicationEntity toEntity() {
        return MenteeApplicationEntity.builder()
                .menteeId(menteeId)
                .admission(admission)
                .build();
    }
}
