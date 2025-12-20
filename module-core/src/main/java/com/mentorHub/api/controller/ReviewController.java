package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.CommentCommandResponse;
import com.mentorHub.api.dto.response.ReviewCommandResponse;
import com.mentorHub.api.dto.response.ReviewResponse;
import com.mentorHub.api.entity.CommentEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.service.MenteeService;
import com.mentorHub.api.service.MenteeShipFacade;
import com.mentorHub.api.service.ReviewService;
import com.mentorHub.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final MenteeShipFacade menteeShipFacade;

    @GetMapping
    public ApiResponse<List<ReviewResponse>> getReviews() {
        List<ReviewEntity> reviews = reviewService.getReviews();

        return ApiResponse.success(reviews.stream()
                .map(ReviewResponse::from)
                .toList());
    }

    @PostMapping
    public ApiResponse<ReviewCommandResponse> setReviews(@Valid @RequestBody ReviewCreateRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = menteeShipFacade.setReviews(request);

        return ApiResponse.success(ReviewCommandResponse.from(en));
    }

    @DeleteMapping
    public ApiResponse<ReviewCommandResponse> deleteReviews(@Valid @RequestBody ReviewDeleteRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = reviewService.deleteReviews(request.toEntity());

        return ApiResponse.success(ReviewCommandResponse.from(en));
    }

    @PutMapping
    public ApiResponse<ReviewCommandResponse> putReviews(@Valid @RequestBody ReviewPutRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = menteeShipFacade.putReviews(request);

        return ApiResponse.success(ReviewCommandResponse.from(en));
    }

    @PostMapping("/comments")
    public ApiResponse<CommentCommandResponse> setComments(@Valid @RequestBody CommentCreateRequest request) {
        log.info("request: {}", request);
        CommentEntity en = reviewService.setComments(request.toEntity());

        return ApiResponse.success(CommentCommandResponse.from(en));
    }

    @DeleteMapping("/comments")
    public ApiResponse<CommentCommandResponse> deleteComments(@Valid @RequestBody CommentDeleteRequest request) {
        log.info("request: {}", request);
        CommentEntity en = reviewService.deleteComments(request.toEntity());

        return ApiResponse.success(CommentCommandResponse.from(en));
    }

    @PutMapping("/comments")
    public ApiResponse<CommentCommandResponse> putComments(@Valid @RequestBody CommentPutRequest request) {
        log.info("request: {}", request);
        CommentEntity en = reviewService.putComments(request.toEntity());

        return ApiResponse.success(CommentCommandResponse.from(en));
    }
}
