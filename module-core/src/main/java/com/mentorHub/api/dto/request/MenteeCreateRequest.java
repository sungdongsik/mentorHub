package com.mentorHub.api.dto.request;


import com.mentorHub.api.entity.MenteeEntity;
import com.util.MenteeRecruitmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    private LocalDateTime startDate;

    private List<String> keyword;

    private String job;

    private MenteeRecruitmentStatus recruitmentStatus;

    public MenteeEntity toEntity() {
        return MenteeEntity.builder()
                .title(title)
                .userId(userId)
                .content(content)
                .name(name)
                .startDate(startDate)
                .keyword(keyword)
                .job(job)
                .recruitmentStatus(recruitmentStatus)
                .build();
    }
}
