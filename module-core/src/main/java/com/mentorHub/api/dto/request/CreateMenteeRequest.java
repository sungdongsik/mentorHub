package com.mentorHub.api.dto.request;


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
@Builder
@ToString
public class CreateMenteeRequest {
    private String title;

    private String content;

    private LocalDateTime startDate;

    private String[] keyword;

    private String job;


    public CreateMenteeRequest() {
    }

    public CreateMenteeRequest(String title, String content, LocalDateTime startDate, String[] keyword, String job) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.keyword = keyword;
        this.job = job;
    }
}
