package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * 25.12.20
 * 댓글 수정 Request Dto
 * 주요 포함 정보:
 * - commentId:  댓글 고유 ID
 * - content: 내용
 */

@Getter
@Builder
public class CommentPutRequest {
    private Long commentId;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .commentId(commentId)
                .content(content)
                .build();
    }
}
