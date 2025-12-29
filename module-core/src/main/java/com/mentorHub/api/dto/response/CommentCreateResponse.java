package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateResponse {
    private Long commentId;

    private Long reviewsId;

    public static CommentCreateResponse from(CommentEntity en) {
        return CommentCreateResponse.builder()
                .commentId(en.getCommentId())
                .reviewsId(en.getReview().getReviewId())
                .build();
    }
}
