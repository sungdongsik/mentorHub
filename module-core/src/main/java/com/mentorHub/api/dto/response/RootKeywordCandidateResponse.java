package com.mentorHub.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RootKeywordCandidateResponse {

    private Long rootKeywordId;

    private String canonicalName;

    private float score;

}
