package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.util.ApplicationStausType;
import lombok.Builder;
import lombok.Getter;

/**
 * 25.11.29
 * 멘티 신청 승인 Response Dto
 * 주요 포함 정보:
 * - menteeId: 멘티 신청 ID
 * - admission: 승인 상태
 */

@Getter
@Builder
public class MenteeApplicationResponse {
    private Long menteeId;

    private ApplicationStausType admission;

    public static MenteeApplicationResponse from(MenteeApplicationEntity en) {
        return MenteeApplicationResponse.builder()
                .menteeId(en.getMenteeId())
                .admission(en.getAdmission())
                .build();
    }
}
