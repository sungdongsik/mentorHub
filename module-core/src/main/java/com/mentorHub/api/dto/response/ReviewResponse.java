package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long reviewsId;

    private Long writingId;

    private String content;

    private String name;

    private LocalDateTime createDate;


    public static ReviewResponse from(ReviewEntity en) {
        return ReviewResponse.builder()
                .reviewsId(en.getReviewId())
                .content(en.getContent())
                .name(en.getName())
                .writingId(en.getMentee().getWritingId())
                .createDate(en.getCreatedDate())
                .build();
    }
}
