package com.mentorHub.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class MenteeSummaryResponse {

    private String name;

    private List<String> skills;
}
