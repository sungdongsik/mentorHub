package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MenteeRepository extends JpaRepository<MenteeEntity, Long> {
    @Modifying
    @Query("update MenteeEntity m set m.delYn = 'Y' where m.writingId = :writingId and m.delYn = 'N'")
    int deleteMentee(@Param("writingId") Long writingId);

    @Query(value = "SELECT * FROM TB_MENTEE " +
            "WHERE JSON_CONTAINS(keyword, :keywordJson) " +
            "ORDER BY RAND() LIMIT 3",
            nativeQuery = true)
    List<MenteeEntity> findChatMentees(@Param("keywordJson") String keywordJson);

    @Query("""
    SELECT m 
    FROM MenteeEntity m 
    WHERE 
    m.keyword = :keyword
    or m.keyword like concat(:keyword, ',%')
    or m.keyword like concat('%,', :keyword)
    or m.keyword like concat('%,', :keyword, ',%')
    """)
    List<MenteeEntity> findByKeyword(@Param("keyword") String keyword);
}
