package com.mentorHub.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MenteeKeywordResponse {
    private String name;

    private List<String> keyword;
}
