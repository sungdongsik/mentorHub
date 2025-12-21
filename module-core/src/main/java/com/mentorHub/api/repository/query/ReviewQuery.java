package com.mentorHub.api.repository.query;

import com.mentorHub.api.entity.ReviewEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mentorHub.api.entity.QMenteeEntity.menteeEntity;
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


    public List<ReviewEntity> getReview() {
        return queryFactory
                .selectFrom(reviewEntity)
                .join(reviewEntity.mentee, menteeEntity).fetchJoin()
                .where(
                        menteeEntity.delYn.eq("N"),
                        reviewEntity.delYn.eq("N")
                )
                .fetch();
    }
}
