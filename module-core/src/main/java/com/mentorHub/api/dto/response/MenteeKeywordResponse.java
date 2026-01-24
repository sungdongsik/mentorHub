package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeKeywordEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenteeKeywordResponse {
    private Long keywordId;

    private String keyword;

    public static MenteeKeywordResponse from(MenteeKeywordEntity en) {
        return MenteeKeywordResponse.builder()
                .keywordId(en.getKeywordId())
                .keyword(en.getKeyword())
                .build();
    }
}
