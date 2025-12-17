package com.mentorHub.api.controller;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.*;
import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.service.MenteeService;
import com.mentorHub.api.service.MenteeShipFacade;
import com.mentorHub.api.service.ReviewService;
import com.mentorHub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 퍼사드 패턴
    private final MenteeShipFacade menteeShipFacade;

    // 단일 도메인
    private final MenteeService menteeService;

    @GetMapping
    public ApiResponse<PageResponse<MenteeResponse>> getMenteesWithReview(@ModelAttribute MenteeRequest request) {
        log.info("request: {}", request);
        List<MenteeEntity> mentees = menteeShipFacade.getMenteesWithReview(request.toEntity());

        List<MenteeResponse> responses = mentees.stream()
                .map(MenteeResponse::from)
                .toList();

        return ApiResponse.success(PageResponse.of(responses));
    }

    @PostMapping
    public ApiResponse<MenteeCommandResponse> setMentees(@RequestBody MenteeCreateRequest request) {
        log.info("request: {}", request);
        MenteeEntity en = menteeService.setMentees(request.toEntity());

        return ApiResponse.success(MenteeCommandResponse.from(en));
    }

    @DeleteMapping
    public ApiResponse<MenteeCommandResponse> deleteMentees(@RequestBody MenteeDeleteRequest request) {
        log.info("request: {}", request);
        MenteeEntity en = menteeService.deleteMentees(request.toEntity());

        return ApiResponse.success(MenteeCommandResponse.from(en));
    }

    @PutMapping
    public ApiResponse<MenteeCommandResponse> putMentees(@RequestBody MenteePutRequest request) {
        log.info("request: {}", request);
        MenteeEntity en = menteeService.putMentees(request.toEntity());

        return ApiResponse.success(MenteeCommandResponse.from(en));
    }

    @PostMapping("/applications")
    public ApiResponse<MenteeApplicationResponse> createMenteesApplication(@RequestBody MenteeApplicationCreateRequest request) {
        log.info("request: {}", request);
        MenteeApplicationEntity en = menteeService.createMenteesApplication(request.toEntity());

        return ApiResponse.success(MenteeApplicationResponse.from(en));
    }

    @PutMapping("/applications/status")
    public ApiResponse<MenteeApplicationResponse> updateApplicationStatus(@RequestBody MenteeApplicationPutRequest request) {
        log.info("request: {}", request);
        MenteeApplicationEntity en = menteeService.updateApplicationStatus(request.toEntity());

        return ApiResponse.success(MenteeApplicationResponse.from(en));
    }
}
