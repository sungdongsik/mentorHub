package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.ReviewEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 25.12.18
 * 리뷰 삭제 Request Dto
 * 주요 포함 정보:
 * - reviewId: 리뷰 ID
 */

@Getter
@Setter
@ToString
public class ReviewDeleteRequest {
    @NotNull(message = "리뷰 ID는 필수입니다.")
    private Long reviewId;

    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .reviewId(reviewId)
                .build();
    }
}
