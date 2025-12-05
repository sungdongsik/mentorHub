package com.mentorHub.api.service;

import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }
}
