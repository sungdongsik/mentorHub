package com.mentorHub.api.repository.query;

import com.mentorHub.api.dto.request.MenteeApplicationPutRequest;
import com.mentorHub.api.entity.QMenteeApplicationEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.util.ApplicationStausType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.mentorHub.api.entity.QMenteeApplicationEntity.*;

@Repository
@RequiredArgsConstructor
public class MenteeApplicationQuery {
    private final JPAQueryFactory jpaQueryFactory;

    public void updateApplicationStatus(MenteeApplicationPutRequest request) {
        jpaQueryFactory.update(menteeApplicationEntity)
                .set(menteeApplicationEntity.admission, request.getAdmission())
                .where(menteeApplicationEntity.menteeId.eq(request.getMenteeId()))
                .execute();
    }
}
