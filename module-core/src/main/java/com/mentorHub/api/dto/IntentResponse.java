package com.mentorHub.api.dto;

import com.util.IntentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class IntentResponse {

    private IntentType intent;      // "MENTEE_SEARCH" or "CHAT"
    private List<String> skills;

    public boolean isMenteeSearch() {
        return IntentType.MENTEE_SEARCH.name().equals(intent.name());
    }
}
