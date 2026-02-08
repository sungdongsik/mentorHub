package com.mentorHub.api.dto;

import com.util.ChatRoleType;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class ChatResponse {
    private ChatRoleType type; // MENTEE_SEARCH / CHAT

    private List<String> skills; // Gemini가 추출한 키워드

    private String message; // Gemini가 만들어준 말

    private List<String> references;

    public boolean isMenteeSearch() {
        return ChatRoleType.MENTEE_SEARCH.name().equals(type.name());
    }

    public List<Long> getWritingIds() {
        if (references == null) return List.of();

        return references.stream()
                .map(ref -> Long.parseLong(ref.split(":")[1]))
                .toList();
    }
}
