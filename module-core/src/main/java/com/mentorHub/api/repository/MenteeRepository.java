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

    @Query("""
    SELECT m 
    FROM MenteeEntity m 
    WHERE 
    m.keyword like concat(:keyword, ',%')
    or m.keyword like concat('%,', :keyword)
    or m.keyword like concat('%,', :keyword, ',%')
    """)
    List<MenteeEntity> findByKeyword(@Param("keyword") String keyword);
}
