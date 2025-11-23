package com.mentorHub.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMenteeResponse {
    private Long writingId;

    private String title;
}
