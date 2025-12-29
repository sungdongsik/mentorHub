package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewPutResponse {

    private Long reviewsId;

    private String title;

    public static ReviewPutResponse from(ReviewEntity en) {
        return ReviewPutResponse.builder()
                .reviewsId(en.getReviewId())
                .title(en.getTitle())
                .build();
    }
}
