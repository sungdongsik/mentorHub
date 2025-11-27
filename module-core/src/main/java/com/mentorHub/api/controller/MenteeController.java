package com.mentorHub.api.controller;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.CreateMenteeRequest;
import com.mentorHub.api.dto.request.MenteeRequest;
import com.mentorHub.api.dto.response.CreateMenteeResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.service.MenteeService;
import com.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 25.11.22
 * 멘티 api
 *
 */

@Slf4j
@RestController
@RequestMapping("/api/mentees")
@RequiredArgsConstructor
public class MenteeController {

    private final MenteeService menteeService;

    @GetMapping
    public ApiResponse<PageResponse<MenteeResponse>> getMentees(@ModelAttribute MenteeRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.getMentees(request));
    }

    @PostMapping
    public ApiResponse<CreateMenteeResponse> setMentees(@RequestBody CreateMenteeRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.setMentees(request));
    }
}
