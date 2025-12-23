package com.mentorHub.api.service;

import com.mentorHub.api.entity.CommentEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.CommentRepository;
import com.mentorHub.api.repository.ReviewRepository;
import com.mentorHub.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<ReviewEntity> getReviews() {
        return reviewRepository.getReviews();
    }

    public ReviewEntity setReviews(ReviewEntity request) {
        return reviewRepository.save(request);
    }

    public ReviewEntity deleteReviews(ReviewEntity request) {

        int deleteReview = reviewRepository.deleteReview(request.getReviewId());

        if (deleteReview == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "이미 삭제되었거나 존재하지 않는 리뷰입니다.");
        }

        return findById(request.getReviewId());
    }

    public ReviewEntity putReviews(ReviewEntity request) {
        return reviewRepository.save(request);
    }

    public ReviewEntity findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다!"));
    }

    @Transactional(readOnly = true)
    public List<ReviewEntity> findByWritingIds(List<Long> writingIds) {
        return reviewRepository.findByMentee_WritingIdIn(writingIds);
    }

    public CommentEntity setComments(CommentEntity request) {
        return commentRepository.save(request);
    }

    public CommentEntity deleteComments(CommentEntity request) {
        CommentEntity en = findById(request);

        commentRepository.delete(en);

        return en;
    }

    private CommentEntity findById(CommentEntity request) {
        return commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다!"));
    }

    public CommentEntity putComments(CommentEntity request) {
        CommentEntity en = findById(request);

        // 내용만 수정
        en.putContent(request.getContent());

        return en;
    }
}
