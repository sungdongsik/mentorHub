package com.mentorHub.api.repository.query;

import com.mentorHub.api.dto.request.MenteeRequest;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.util.UserType;
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

    public List<MenteeResponse> getMentees(MenteeRequest request) {

        return queryFactory.select(Projections.bean(
                        MenteeResponse.class,
                        menteeEntity.writingId,
                        userEntity.name,
                        userEntity.status,
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
                                .and(menteeEntity.delYn.eq("N"))
                                .and(userEntity.delYn.eq("N"))
                )
                .fetch();
    }

    private BooleanBuilder builder(MenteeRequest request) {

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(request.getName())) {
            builder.and(userEntity.name.contains(request.getName()));
        }

        if (request.getKeyword() != null) {
            builder.and(menteeEntity.keyword.in(request.getKeyword()));
        }

        if (request.getStatus() != null) {
            builder.and(userEntity.status.eq(request.getStatus()));
        }

        if (request.getStartDate() != null) {
            builder.and(menteeEntity.startDate.goe(request.getStartDate()));
        }

        return builder;
    }
}
