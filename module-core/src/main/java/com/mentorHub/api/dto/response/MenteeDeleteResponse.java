package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenteeDeleteResponse {
    private Long writingId;

    private String title;

    public static MenteeDeleteResponse from(MenteeEntity en) {
        return MenteeDeleteResponse.builder()
                .writingId(en.getWritingId())
                .title(en.getTitle())
                .build();
    }
}
