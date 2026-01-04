package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.CommentEntity;
import com.mentorHub.api.entity.ReviewEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * 25.12.20
 * 댓글 달기 Request Dto
 * 주요 포함 정보:
 * - reviewsId: 리뷰 고유 ID
 * - userId: 유저 고유 ID
 * - parentId: 부모 댓글 ID
 * - content: 내용
 */

@Getter
@Builder
public class CommentCreateRequest {
    @NotNull(message = "리뷰 ID는 필수입니다.")
    private Long reviewsId;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    private Long parentId;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private String name;

    public CommentEntity toEntity(ReviewEntity en) {
        return CommentEntity.builder()
                .review(en)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .name(name)
                .build();
    }
}
