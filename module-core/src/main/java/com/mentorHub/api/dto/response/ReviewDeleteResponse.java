package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDeleteResponse {

    private Long reviewsId;

    private String title;

    public static ReviewDeleteResponse from(ReviewEntity en) {
        return ReviewDeleteResponse.builder()
                .reviewsId(en.getReviewId())
                .title(en.getTitle())
                .build();
    }
}
