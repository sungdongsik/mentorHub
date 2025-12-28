package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 25.11.29
 * 멘티 삭제 Request Dto
 * 주요 포함 정보:
 * - writingId: 멘티 글 고유 ID
 * - userId: 사용자 고유 ID
 */

@Getter
@Setter
@ToString
public class MenteeDeleteRequest {
    @NotNull(message = "멘티 글 ID는 필수입니다.")
    private Long writingId;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    public MenteeEntity toEntity() {
        return MenteeEntity.builder()
                .writingId(writingId)
                .userId(userId)
                .build();
    }
}
