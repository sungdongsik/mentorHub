package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenteeCommandResponse {
    private Long writingId;

    private String title;

    public static MenteeCommandResponse from(MenteeEntity en) {
        return MenteeCommandResponse.builder()
                .writingId(en.getWritingId())
                .title(en.getTitle())
                .build();
    }
}
