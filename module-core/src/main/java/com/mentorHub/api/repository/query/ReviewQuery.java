package com.mentorHub.api.repository.query;

import com.mentorHub.api.entity.QReviewEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mentorHub.api.entity.QReviewEntity.reviewEntity;

@Repository
@RequiredArgsConstructor
public class ReviewQuery {

    private final JPAQueryFactory queryFactory;

    public List<ReviewEntity> findByWritingIds(List<Long> writingIds) {
        return queryFactory.select(reviewEntity)
                .from(reviewEntity)
                .where(reviewEntity.mentee.writingId.in(writingIds))
                .fetch();
    }
}
