package com.mentorHub.api.repository;

import com.mentorHub.api.dto.response.MenteeKeywordResponse;
import com.mentorHub.api.entity.MenteeEntity;
import com.util.MenteeRecruitmentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenteeRepository extends JpaRepository<MenteeEntity, Long> {
    @Modifying
    @Query("update MenteeEntity m set m.delYn = 'Y' where m.writingId = :writingId")
    int deleteMentee(@Param("writingId") Long writingId);

    @Query("""
    SELECT new com.mentorHub.api.dto.response.MenteeKeywordResponse(
        m.name,
        m.keyword
    )
    FROM MenteeEntity m
    WHERE m.recruitmentStatus = :recruitmentStatus
    ORDER BY m.createdDate DESC
    """)
    List<MenteeKeywordResponse> findTopWithKeywords(@Param("recruitmentStatus") MenteeRecruitmentStatus recruitmentStatus, Pageable pageable);
}
