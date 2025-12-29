package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenteeCreateResponse {
    private Long writingId;

    private String title;

    public static MenteeCreateResponse from(MenteeEntity en) {
        return MenteeCreateResponse.builder()
                .writingId(en.getWritingId())
                .title(en.getTitle())
                .build();
    }
}
