package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 25.11.29
 * 멘티 수정 Request Dto
 * 주요 포함 정보:
 * - writingId: 멘티 글 고유 ID
 * - title: 제목
 * - content: 내용
 * - startDate: 시작일
 * - keyword: 키워드
 * - job: 직업
 */
@Getter
@ToString
public class MenteePutRequest {
    private Long writingId;

    private String title;

    private String content;

    private LocalDateTime startDate;

    private String[] keyword;

    private String job;

    // Entity 선언해서 값 넣어주기~
    public MenteeEntity toEntity() {
         return MenteeEntity.builder()
                 .writingId(writingId)
                 .title(title)
                 .content(content)
                 .startDate(startDate)
                 .keyword(keyword)
                 .job(job)
                 .build();
    }
}
