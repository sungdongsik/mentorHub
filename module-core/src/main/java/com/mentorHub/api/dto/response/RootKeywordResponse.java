package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RootKeywordResponse {
    private Long rootKeywordId;

    private String canonicalName;

    private RootKeywordAliasStatus status;

    public static RootKeywordResponse from(RootKeywordEntity en) {
        return RootKeywordResponse.builder()
                .rootKeywordId(en.getRootKeywordId())
                .canonicalName(en.getCanonicalName())
                .status(en.getStatus())
                .build();
    }
}
