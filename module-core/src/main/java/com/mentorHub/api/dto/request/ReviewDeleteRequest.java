package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDeleteRequest {
    private Long reviewId;

    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .reviewId(reviewId)
                .build();
    }
}
