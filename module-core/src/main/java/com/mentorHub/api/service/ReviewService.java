package com.mentorHub.api.service;

import com.mentorHub.Mapper.CommentMapper;
import com.mentorHub.api.dto.response.CommentResponse;
import com.mentorHub.api.entity.CommentEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.CommentRepository;
import com.mentorHub.api.repository.ReviewRepository;
import com.mentorHub.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        ReviewEntity en = findById(request.getReviewId());

        int deleteReview = reviewRepository.deleteReview(en.getReviewId());

        if (deleteReview == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "이미 삭제되었거나 존재하지 않는 리뷰입니다.");
        }

        return en;
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

    // 대댓글 최소 1depth 까지만 저장하기
    public CommentEntity setComments(CommentEntity request) {
        // parentId 없으면 댓글 작성임 → 바로 저장
        if (request.getParentId() == null) {
            return commentRepository.save(request);
        }

        // parentId 있으면 부모 조회
        CommentEntity en = findByComment(request.getParentId());

        // 부모가 이미 대댓글이면 차단
        if (en.getParentId() != null)
            throw new BusinessException(HttpStatus.BAD_REQUEST, "대댓글에는 대댓글을 작성할 수 없습니다.");

        return commentRepository.save(request);
    }

    public CommentEntity deleteComments(CommentEntity request) {
        CommentEntity en = findByComment(request.getCommentId());

        commentRepository.delete(en);

        return en;
    }

    private CommentEntity findByComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다!"));
    }

    public CommentEntity putComments(CommentEntity request) {
        CommentEntity en = findByComment(request.getCommentId());

        // 내용만 수정
        en.putContent(request.getContent());

        return en;
    }
}
