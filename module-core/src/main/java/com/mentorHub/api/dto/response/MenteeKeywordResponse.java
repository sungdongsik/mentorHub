package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class MenteeKeywordResponse {
    private String name;

    private List<String> keyword;

    public static MenteeKeywordResponse from(MenteeEntity en) {
        return MenteeKeywordResponse.builder()
                .name(en.getName())
                .keyword(
                        en.getKeywords().stream()
                                .map(MenteeKeywordEntity::getKeyword)
                                .toList()
                )
                .build();
    }


    public static List<MenteeKeywordResponse> fromList(List<MenteeEntity> entities) {
        return entities.stream()
                .map(MenteeKeywordResponse::from)
                .toList();
    }
}
