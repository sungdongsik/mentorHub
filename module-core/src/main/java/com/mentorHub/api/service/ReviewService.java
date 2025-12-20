package com.mentorHub.api.service;

import com.mentorHub.api.entity.CommentEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.CommentRepository;
import com.mentorHub.api.repository.ReviewRepository;
import com.mentorHub.api.repository.query.ReviewQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewQuery reviewQuery;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    public ReviewEntity setReviews(ReviewEntity request) {
        return reviewRepository.save(request);
    }

    public ReviewEntity deleteReviews(ReviewEntity request) {
        ReviewEntity en = findById(request);

        reviewRepository.delete(en);

        return en;
    }

    public ReviewEntity putReviews(ReviewEntity request) {
        return reviewRepository.save(request);
    }

    private ReviewEntity findById(ReviewEntity request) {
        return reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다!"));
    }

    @Transactional(readOnly = true)
    public List<ReviewEntity> findByWritingIds(List<Long> writingIds) {
        return reviewQuery.findByWritingIds(writingIds);
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
