package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 25.12.06
 * 리뷰 등록 Request Dto
 * 주요 포함 정보:
 * - writingId: 멘티 글 ID
 * - title: 제목
 * - content: 내용
 * - rating: 평점
 */


@Getter
@Builder
@ToString
public class ReviewCreateRequest {
    @NotNull(message = "멘티 글 ID는 필수입니다.")
    private Long writingId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하여야 합니다.")
    private double rating;

    private String name;

    public ReviewEntity toEntity(MenteeEntity en) {
        return ReviewEntity.builder()
                .title(title)
                .content(content)
                .mentee(en)
                .rating(rating)
                .name(name)
                .build();
    }
}
