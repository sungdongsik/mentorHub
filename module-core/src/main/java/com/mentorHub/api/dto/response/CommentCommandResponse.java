package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCommandResponse {
    private Long commentId;

    private Long reviewsId;

    public static CommentCommandResponse from(CommentEntity en) {
        return CommentCommandResponse.builder()
                .commentId(en.getCommentId())
                .reviewsId(en.getReview().getReviewId())
                .build();
    }
}
