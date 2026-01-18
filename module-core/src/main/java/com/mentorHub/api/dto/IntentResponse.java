package com.mentorHub.api.dto;

import com.util.IntentType;
import lombok.Getter;

import java.util.List;

@Getter
public class IntentResponse {
    private String intent; // MENTEE_SEARCH / CHAT

    private List<String> keywords; // Gemini가 추출한 키워드

    private String answer; // Gemini가 만들어준 말

    public boolean isMenteeSearch() {
        return IntentType.MENTEE_SEARCH.name().equals(intent);
    }
}
