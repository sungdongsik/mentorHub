package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenteePutResponse {
    private Long writingId;

    private String title;

    public static MenteePutResponse from(MenteeEntity en) {
        return MenteePutResponse.builder()
                .writingId(en.getWritingId())
                .title(en.getTitle())
                .build();
    }
}
