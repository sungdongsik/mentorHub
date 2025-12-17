package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.ReviewCreateRequest;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    // 테스트 대상
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MenteeRepository menteeRepository;

    @Test
    @DisplayName("리뷰 등록하기")
    public void setReview() throws Exception{
        // 더미 데이터 생성
        Long writingId = 6L;
        String title = "상냥해요~";
        String content = "잘 가르쳐줘요~~~";

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .writingId(writingId)
                .title(title)
                .content(content)
                .build();

        MenteeEntity en = menteeRepository.findById(writingId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다!"));

        // 가짜 행동 정의해주기
        given(reviewRepository.save(any(ReviewEntity.class)))
                .willReturn(request.toEntity(en));

        ReviewEntity response = reviewService.setReviews(request.toEntity(en));

        // 검증하기
        assertThat(response.getMentee().getWritingId()).isEqualTo(writingId);
        assertThat(response.getTitle()).isEqualTo(title);

        // 2. Repository의 save() 메서드가 실제로 1번 호출되었는지 검증
        verify(reviewRepository, times(1)).save(any(ReviewEntity.class));
    }

    @Test
    @DisplayName("리뷰 삭제하기")
    void deleteReview() {
        Long reviewId = 1L;

        ReviewEntity en = ReviewEntity.builder()
                .reviewId(reviewId)
                .build();

        ReviewEntity result = reviewService.deleteReviews(en);

        // 검증
        verify(reviewService, times(1)).deleteReviews(en);

        // 2. 반환된 응답의 필드 검증
        assertThat(result.getReviewId()).isEqualTo(reviewId);
        assertThat(result.getTitle()).isNull();
    }

}