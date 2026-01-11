package com.mentorHub.api.repository.query;

import com.mentorHub.api.entity.MenteeEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mentorHub.api.entity.QMenteeEntity.*;
import static com.mentorHub.api.entity.QUserEntity.*;

@Repository
@RequiredArgsConstructor
public class MenteeQuery {

    private final JPAQueryFactory queryFactory;

    public List<MenteeEntity> getMentees(MenteeEntity request) {

        return queryFactory.select(Projections.bean(
                        MenteeEntity.class,
                        menteeEntity.writingId,
                        userEntity.name,
                        menteeEntity.startDate,
                        menteeEntity.keyword,
                        menteeEntity.title,
                        menteeEntity.content,
                        menteeEntity.job
                ))
                .from(userEntity)
                .leftJoin(menteeEntity).on(userEntity.userId.eq(menteeEntity.userId))
                .where(
                        builder(request)
                )
                .fetch();
    }

    private BooleanBuilder builder(MenteeEntity request) {

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(request.getName())) {
            builder.and(userEntity.name.contains(request.getName()));
        }

        if (request.getStartDate() != null) {
            builder.and(menteeEntity.startDate.goe(request.getStartDate()));
        }

        return builder;
    }
}
