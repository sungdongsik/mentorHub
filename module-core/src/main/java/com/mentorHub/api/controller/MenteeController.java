package com.mentorHub.api.controller;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.CreateMenteeRequest;
import com.mentorHub.api.dto.request.MenteeRequest;
import com.mentorHub.api.dto.response.CreateMenteeResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.service.MenteeService;
import com.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 25.11.22
 * 멘티 api
 *
 */

@RestController
@RequestMapping("/api/mentors")
@Slf4j
public class MenteeController {

    private final MenteeService menteeService;

    // lombok 라이브러리를 사용할까..? 흠..
    public MenteeController(MenteeService menteeService) {
        this.menteeService = menteeService;
    }

    @GetMapping
    public ApiResponse<PageResponse<MenteeResponse>> getMentors(MenteeRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.getMentees(request));
    }

    @PostMapping
    public ApiResponse<CreateMenteeResponse> setMentors() {
        CreateMenteeRequest request = CreateMenteeRequest.builder()
                .title("제목")
                .content("내용")
                .keyword(new String[]{"java", "jpa", "python"})
                .startDate(LocalDateTime.now())
                .job("취준생")
                .build();
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.setMentors(request));
    }
}
