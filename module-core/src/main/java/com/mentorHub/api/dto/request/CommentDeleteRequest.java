package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.CommentEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * 25.12.20
 * 댓글 삭제 Request Dto
 * 주요 포함 정보:
 * - commentId: 댓글 고유 ID
 */

@Getter
@Builder
public class CommentDeleteRequest {
    @NotNull(message = "댓글 ID는 필수입니다.")
    private Long commentId;

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .commentId(commentId)
                .build();
    }
}
