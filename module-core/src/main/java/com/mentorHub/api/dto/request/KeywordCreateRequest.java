package com.mentorHub.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordCreateRequest {
    private String keyword;
}
