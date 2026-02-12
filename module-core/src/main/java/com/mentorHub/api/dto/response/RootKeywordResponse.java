package com.mentorHub.api.dto.response;

import com.util.RootKeywordAliasStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RootKeywordResponse {
    private Long rootKeywordAliasId;

    private String aliasName;

    private RootKeywordAliasStatus status;

    private List<RootKeywordCandidateResponse> candidateRoots;

    /*public static RootKeywordResponse from(RootKeywordEntity en) {
        return RootKeywordResponse.builder()
                .rootKeywordId(en.getRootKeywordId())
                .canonicalName(en.getCanonicalName())
                .status(en.getStatus())
                .build();
    }*/
}
