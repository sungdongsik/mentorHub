package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;

    private Long userId;

    private Long parentId;

    private String name;

    private Long reviewId;

    private LocalDateTime createDate;

    private List<CommentResponse> children;  // 대댓글 목록

    public static CommentResponse from(CommentEntity en) {
        return CommentResponse.builder()
                .commentId(en.getCommentId())
                .userId(en.getUserId())
                .parentId(en.getParentId())
                .name(en.getName())
                .reviewId(en.getReview().getReviewId())
                .createDate(en.getCreatedDate())
                .children(new ArrayList<>())
                .build();
    }
}
