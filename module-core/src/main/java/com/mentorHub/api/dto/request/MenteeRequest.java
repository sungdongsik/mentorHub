package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 25.11.21
 * 멘티 조건 Request Dto
 * 주요 포함 정보:
 * - title: 제목
 * - keyword: 관심 키워드 목록
 * - status: 멘토링 상태
 * - startDate: 시작일
 */

@Getter
@Setter
@ToString
public class MenteeRequest {
    private String title;

    private String keyword;

    private LocalDateTime startDate;

    public MenteeEntity toEntity() {
        return MenteeEntity.builder()
                .title(title)
                .keyword(keyword)
                .startDate(startDate)
                .build();
    }
}
