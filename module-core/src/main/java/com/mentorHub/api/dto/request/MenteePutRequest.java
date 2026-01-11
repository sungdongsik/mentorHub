package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import com.util.MenteeRecruitmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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
    @NotNull(message = "멘티 글 ID는 필수입니다.")
    private Long writingId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private LocalDateTime startDate;

    private List<String> keyword;

    private String job;

    private MenteeRecruitmentStatus recruitmentStatus;

    // Entity 선언해서 값 넣어주기~
    public MenteeEntity toEntity() {
         return MenteeEntity.builder()
                 .writingId(writingId)
                 .title(title)
                 .content(content)
                 .startDate(startDate)
                 .keyword(keyword)
                 .job(job)
                 .recruitmentStatus(recruitmentStatus)
                 .build();
    }
}
