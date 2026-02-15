package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class MenteeSummaryResponse {

    private String name;

    private List<String> skills;

    public static MenteeSummaryResponse from(MenteeEntity mentee) {
        return new MenteeSummaryResponse(
                mentee.getName(),
                mentee.getKeywords().stream()
                        .map(MenteeKeywordEntity::getKeyword)
                        .toList()
        );
    }
}
