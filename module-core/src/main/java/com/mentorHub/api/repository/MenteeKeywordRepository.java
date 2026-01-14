package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenteeKeywordRepository extends JpaRepository<MenteeKeywordEntity, Long> {

    // writingId 리스트에 포함된 모든 키워드 엔티티 조회
    @Query("SELECT mk FROM MenteeKeywordEntity mk WHERE mk.mentee.writingId IN :writingIds")
    List<MenteeKeywordEntity> findAllByMenteeWritingIdIn(List<Long> writingIds);
}
