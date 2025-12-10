package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewPutRequest {

    private Long reviewId;

    private Long writingId;

    private String title;

    private String content;

    private double rating;

    public ReviewEntity toEntity(MenteeEntity en) {
        return ReviewEntity.builder()
                .reviewId(reviewId)
                .title(title)
                .mentee(en)
                .content(content)
                .rating(rating)
                .build();
    }
}
