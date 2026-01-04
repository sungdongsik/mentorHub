package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.*;
import com.mentorHub.api.entity.CommentEntity;
import com.mentorHub.api.entity.ReviewEntity;
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
    public ApiResponse<ReviewCreateResponse> setReviews(@Valid @RequestBody ReviewCreateRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = menteeShipFacade.setReviews(request);

        return ApiResponse.success(ReviewCreateResponse.from(en));
    }

    @DeleteMapping
    public ApiResponse<ReviewDeleteResponse> deleteReviews(@Valid @RequestBody ReviewDeleteRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = reviewService.deleteReviews(request.toEntity());

        return ApiResponse.success(ReviewDeleteResponse.from(en));
    }

    @PutMapping
    public ApiResponse<ReviewPutResponse> putReviews(@Valid @RequestBody ReviewPutRequest request) {
        log.info("request: {}", request);
        ReviewEntity en = menteeShipFacade.putReviews(request);

        return ApiResponse.success(ReviewPutResponse.from(en));
    }

    @PostMapping("/comments")
    public ApiResponse<CommentCreateResponse> setComments(@Valid @RequestBody CommentCreateRequest request) {
        log.info("request: {}", request);
        ReviewEntity review = reviewService.findById(request.getReviewsId());
        CommentEntity en = reviewService.setComments(request.toEntity(review));

        return ApiResponse.success(CommentCreateResponse.from(en));
    }

    @DeleteMapping("/comments")
    public ApiResponse<CommentDeleteResponse> deleteComments(@Valid @RequestBody CommentDeleteRequest request) {
        log.info("request: {}", request);
        CommentEntity en = reviewService.deleteComments(request.toEntity());

        return ApiResponse.success(CommentDeleteResponse.from(en));
    }

    @PutMapping("/comments")
    public ApiResponse<CommentPutResponse> putComments(@Valid @RequestBody CommentPutRequest request) {
        log.info("request: {}", request);
        CommentEntity en = reviewService.putComments(request.toEntity());

        return ApiResponse.success(CommentPutResponse.from(en));
    }
}
