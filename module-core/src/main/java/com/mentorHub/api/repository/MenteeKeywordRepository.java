package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import com.util.MenteeRecruitmentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenteeKeywordRepository extends JpaRepository<MenteeKeywordEntity, Long> {

    // writingId 리스트에 포함된 모든 키워드 엔티티 조회
    @Query("SELECT mk FROM MenteeKeywordEntity mk WHERE mk.mentee.writingId IN :writingIds")
    List<MenteeKeywordEntity> findAllByMenteeWritingIdIn(@Param("writingIds") List<Long> writingIds);

    @Query("""
    SELECT DISTINCT m
    FROM MenteeEntity m
    JOIN m.keywords mk
    WHERE mk.keyword IN :keywords
    AND m.recruitmentStatus = :recruitmentStatus
    ORDER BY m.createdDate DESC
    """)
    List<MenteeEntity> findByKeywords(@Param("keywords") List<String> keywords, @Param("recruitmentStatus") MenteeRecruitmentStatus recruitmentStatus,
                                      Pageable pageable);
}
