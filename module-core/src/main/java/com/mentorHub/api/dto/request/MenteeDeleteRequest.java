package com.mentorHub.api.dto.request;

import lombok.Getter;
import lombok.ToString;

/**
 * 25.11.29
 * 멘티 삭제 Request Dto
 * 주요 포함 정보:
 * - writingId: 멘티 글 고유 ID
 * - userId: 사용자 고유 ID
 */

@Getter
@ToString
public class MenteeDeleteRequest {
    private Long writingId;

    private Long userId;
}
