package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDeleteResponse {
    private Long commentId;

    private Long reviewsId;

    public static CommentDeleteResponse from(CommentEntity en) {
        return CommentDeleteResponse.builder()
                .commentId(en.getCommentId())
                .reviewsId(en.getReview().getReviewId())
                .build();
    }
}
