package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MenteeRepository extends JpaRepository<MenteeEntity, Long> {
    @Modifying
    @Query("update MenteeEntity m set m.delYn = 'Y' where m.writingId = :writingId")
    int deleteMentee(@Param("writingId") Long writingId);

    // MenteeKeyword 리스트 안의 RootKeyword ID를 찾아가는 규칙
    List<MenteeEntity> findDistinctByKeywords_RootKeyword_RootKeywordId(Long rootKeywordId);
}
