package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentPutResponse {
    private Long commentId;

    private Long reviewsId;

    public static CommentPutResponse from(CommentEntity en) {
        return CommentPutResponse.builder()
                .commentId(en.getCommentId())
                .reviewsId(en.getReview().getReviewId())
                .build();
    }
}
