package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCreateResponse {

    private Long reviewsId;

    private String title;

    public static ReviewCreateResponse from(ReviewEntity en) {
        return ReviewCreateResponse.builder()
                .reviewsId(en.getReviewId())
                .title(en.getTitle())
                .build();
    }
}
