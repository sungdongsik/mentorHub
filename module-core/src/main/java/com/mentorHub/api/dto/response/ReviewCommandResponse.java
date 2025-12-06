package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCommandResponse {

    private Long reviewsId;

    private String title;

    public static ReviewCommandResponse from(ReviewEntity en) {
        return ReviewCommandResponse.builder()
                .reviewsId(en.getReviewId())
                .title(en.getTitle())
                .build();
    }
}
