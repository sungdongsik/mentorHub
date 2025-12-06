package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewPutRequest {

    private Long reviewId;

    private String title;

    private String content;

    private double rating;

    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .reviewId(reviewId)
                .title(title)
                .content(content)
                .rating(rating)
                .build();
    }
}
