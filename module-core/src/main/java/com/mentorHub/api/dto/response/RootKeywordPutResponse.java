package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RootKeywordPutResponse {
    private Long rootKeywordId;

    private RootKeywordAliasStatus status;

    public static RootKeywordPutResponse from(RootKeywordEntity en) {
        return RootKeywordPutResponse.builder()
                .rootKeywordId(en.getRootKeywordId())
                .status(en.getStatus())
                .build();
    }
}
