package com.mentorHub.api.dto.request;


import com.mentorHub.api.entity.MenteeEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 25.11.23
 * 멘티 글 등록 Request Dto
 * 주요 포함 정보:
 * - title: 제목
 * - content: 내용
 * - startDate: 시작일
 * - keyword: 키워드
 * - job: 직업
 */

@Getter
@Setter
@ToString
public class MenteeCreateRequest {
    private String title;

    private Long userId;

    private String content;

    private LocalDateTime startDate;

    private String[] keyword;

    private String job;


    public MenteeEntity toEntity() {
        return MenteeEntity.builder()
                .title(title)
                .userId(userId)
                .content(content)
                .startDate(startDate)
                .keyword(keyword)
                .job(job)
                .build();
    }
}
