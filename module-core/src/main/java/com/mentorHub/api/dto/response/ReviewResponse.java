package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long reviewsId;

    private Long menteeId;

    private String content;

    private String name;

    private LocalDateTime insertDate;


    public static ReviewResponse from(ReviewEntity en) {
        return ReviewResponse.builder()
                .reviewsId(en.getReviewId())
                .menteeId(en.getMenteeId())
                .content(en.getContent())
                .name(en.getName())
                .insertDate(en.getCreatedDate())
                .build();
    }
}
