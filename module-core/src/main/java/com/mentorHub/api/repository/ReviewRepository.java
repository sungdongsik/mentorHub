package com.mentorHub.api.repository;

import com.mentorHub.api.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByMentee_WritingIdIn(List<Long> writingIds);

    // 리뷰를 조회할 때 멘티도 한 번에 로딩하여 N+1 문제 방지
    // r.getMentee() 접근 시 추가 쿼리가 발생하지 않음
    @Query("""
        select r from ReviewEntity r
        join fetch r.mentee m
        where r.delYn = 'N'
          and m.delYn = 'N'
    """)
    List<ReviewEntity> getReviews();
}
