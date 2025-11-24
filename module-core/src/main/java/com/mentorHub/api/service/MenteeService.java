package com.mentorHub.api.service;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.CreateMenteeRequest;
import com.mentorHub.api.dto.request.MenteeRequest;
import com.mentorHub.api.dto.response.CreateMenteeResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.dto.response.ReviewsResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenteeService {

    public PageResponse<MenteeResponse> getMentees(MenteeRequest request) {
        List<MenteeResponse> list = new ArrayList<>();

        for (long i = 1; i <= 20; i++) {
            list.add(MenteeResponse.builder()
                    .menteeId(i)
                    .name("Mentee" + i)
                    .status(i % 2 == 0 ? "MENTEE" : "MENTO")
                    .startDate(LocalDateTime.now().minusDays(i))
                    .keyword(new String[]{"Java", "Spring", "Backend"})
                    .title("Title " + i)
                    .content("Content for mentee " + i)
                    .job("Developer")
                    .reviews(List.of(new ReviewsResponse()))
                    .build());
        }

        return PageResponse.of(list);
    }

    public CreateMenteeResponse setMentors(CreateMenteeRequest request){
        return CreateMenteeResponse.builder()
                .writingId(1L)
                .title(request.getTitle())
                .build();
    }


}
