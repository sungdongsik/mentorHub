package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 25.11.21
 * 리뷰 리스트 조회 Response Dto
 * 주요 포함 정보:
 * - reviewsId: 리뷰 고유 ID
 * - writingId: 리뷰를 작성한 글 ID
 * - content: 리뷰 내용
 * - name: 작성자 이름
 * - createdDate: 리뷰 등록일
 */

@Getter
@Builder
public class ReviewsResponse {
    private Long reviewId;

    private Long writingId;

    private String content;

    private String name;

    private LocalDateTime createdDate;

    public static ReviewsResponse from(ReviewEntity en) {
        return ReviewsResponse.builder()
                .reviewId(en.getReviewId())
                .writingId(en.getMentee().getWritingId())
                .content(en.getContent())
                .name(en.getName())
                .createdDate(en.getCreatedDate())
                .build();
    }
}
