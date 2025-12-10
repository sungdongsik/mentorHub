package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.ReviewCreateRequest;
import com.mentorHub.api.dto.request.ReviewDeleteRequest;
import com.mentorHub.api.dto.request.ReviewPutRequest;
import com.mentorHub.api.dto.response.ReviewCommandResponse;
import com.mentorHub.api.dto.response.ReviewResponse;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.service.MenteeShipService;
import com.mentorHub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final MenteeShipService menteeShipService;

    @GetMapping
    public ApiResponse<List<ReviewResponse>> getReviews() {
        List<ReviewEntity> reviews = menteeShipService.getReviews();

        return ApiResponse.success(reviews.stream()
                .map(ReviewResponse::from)
                .toList());
    }

    @PostMapping
    public ApiResponse<ReviewCommandResponse> setReviews(@RequestBody ReviewCreateRequest request) {
        log.info("request: {}", request);
        MenteeEntity mentee = menteeShipService.findById(request.getWritingId());
        ReviewEntity en = menteeShipService.setReviews(request.toEntity(mentee));

        return ApiResponse.success(ReviewCommandResponse.from(en));
    }

    @DeleteMapping
    public ApiResponse<ReviewCommandResponse> deleteReviews(@RequestBody ReviewDeleteRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = menteeShipService.deleteReviews(request.toEntity());

        return ApiResponse.success(ReviewCommandResponse.from(en));
    }

    @PutMapping
    public ApiResponse<ReviewCommandResponse> putReviews(@RequestBody ReviewPutRequest request) {
        log.info("request: {}", request);
        MenteeEntity mentee = menteeShipService.findById(request.getWritingId());
        ReviewEntity en = menteeShipService.putReviews(request.toEntity(mentee));

        return ApiResponse.success(ReviewCommandResponse.from(en));
    }
}
