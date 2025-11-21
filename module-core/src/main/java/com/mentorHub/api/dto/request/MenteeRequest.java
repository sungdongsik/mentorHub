package com.mentorHub.api.dto.request;

import java.time.LocalDateTime;

/**
 * 25.11.21
 * 멘티 조건 Request Dto
 * 주요 포함 정보:
 * - name: 멘티 이름
 * - keyword: 관심 키워드 목록
 * - status: 멘토링 상태
 * - startDate: 시작일
 */

public class MenteeRequest {
    private String name;

    private String keyword;

    private String status;

    private LocalDateTime startDate;
}
