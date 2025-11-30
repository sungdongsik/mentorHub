package com.mentorHub.api.controller;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.MenteeApplicationResponse;
import com.mentorHub.api.dto.response.MenteeCommandResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.service.MenteeService;
import com.mentorHub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<MenteeCommandResponse> setMentees(@RequestBody MenteeCreateRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.setMentees(request));
    }

    @DeleteMapping
    public ApiResponse<MenteeCommandResponse> deleteMentees(@RequestBody MenteeDeleteRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.deleteMentees(request));
    }

    @PutMapping
    public ApiResponse<MenteeCommandResponse> putMentees(@RequestBody MenteePutRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.putMentees(request));
    }

    @PostMapping("/applications")
    public ApiResponse<MenteeApplicationResponse> createMenteesApplication(@RequestBody MenteeApplicationCreateRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.createMenteesApplication(request));
    }

    @PutMapping("/applications/status")
    public ApiResponse<MenteeApplicationResponse> updateApplicationStatus(@RequestBody MenteeApplicationPutRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeService.updateApplicationStatus(request));
    }
}
