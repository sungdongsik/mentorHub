package com.mentorHub.api.controller;

import com.mentorHub.api.dto.response.ReviewResponse;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.service.ReviewService;
import com.mentorHub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    
    @GetMapping
    public ApiResponse<List<ReviewResponse>> getReviews() {
        List<ReviewEntity> reviews = reviewService.getReviews();

        return ApiResponse.success(reviews.stream()
                .map(ReviewResponse::from)
                .toList());
    }
}
