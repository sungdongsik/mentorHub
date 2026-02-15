package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
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

    public static RootKeywordResponse from(RootKeywordAliasEntity en, List<RootKeywordCandidateResponse> candidates) {
        return RootKeywordResponse.builder()
                .rootKeywordAliasId(en.getRootKeywordAliasId())
                .aliasName(en.getAliasName())
                .status(en.getStatus())
                .candidateRoots(candidates)
                .build();
    }
}
