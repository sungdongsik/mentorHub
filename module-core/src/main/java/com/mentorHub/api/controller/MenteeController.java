package com.mentorHub.api.controller;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.MenteeApplicationResponse;
import com.mentorHub.api.dto.response.MenteeCommandResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.service.MenteeShipService;
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

    private final MenteeShipService menteeShipService;

    @GetMapping
    public ApiResponse<PageResponse<MenteeResponse>> getMentees(@ModelAttribute MenteeRequest request) {

        List<MenteeEntity> mentees = menteeShipService.getMentees(request.toEntity());
        List<ReviewEntity> reviews = menteeShipService.getReviews();
        List<MenteeEntity> menteesWithReviews = menteeShipService.getMenteesWithReviews(mentees, reviews);

        List<MenteeResponse> responses = menteesWithReviews.stream()
                .map(MenteeResponse::from)
                .toList();

        return ApiResponse.success(PageResponse.of(responses));
    }

    @PostMapping
    public ApiResponse<MenteeCommandResponse> setMentees(@RequestBody MenteeCreateRequest request) {
        log.info("request: {}", request);
        MenteeEntity en = menteeShipService.setMentees(request.toEntity());

        return ApiResponse.success(MenteeCommandResponse.from(en));
    }

    @DeleteMapping
    public ApiResponse<MenteeCommandResponse> deleteMentees(@RequestBody MenteeDeleteRequest request) {
        log.info("request: {}", request);
        MenteeEntity en = menteeShipService.deleteMentees(request.toEntity());

        return ApiResponse.success(MenteeCommandResponse.from(en));
    }

    @PutMapping
    public ApiResponse<MenteeCommandResponse> putMentees(@RequestBody MenteePutRequest request) {
        log.info("request: {}", request);
        MenteeEntity en = menteeShipService.putMentees(request.toEntity());

        return ApiResponse.success(MenteeCommandResponse.from(en));
    }

    @PostMapping("/applications")
    public ApiResponse<MenteeApplicationResponse> createMenteesApplication(@RequestBody MenteeApplicationCreateRequest request) {
        log.info("request: {}", request);
        MenteeApplicationEntity en = menteeShipService.createMenteesApplication(request.toEntity());

        return ApiResponse.success(MenteeApplicationResponse.from(en));
    }

    @PutMapping("/applications/status")
    public ApiResponse<MenteeApplicationResponse> updateApplicationStatus(@RequestBody MenteeApplicationPutRequest request) {
        log.info("request: {}", request);
        MenteeApplicationEntity en = menteeShipService.updateApplicationStatus(request.toEntity());

        return ApiResponse.success(MenteeApplicationResponse.from(en));
    }
}
