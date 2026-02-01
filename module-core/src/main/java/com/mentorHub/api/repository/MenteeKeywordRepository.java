package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenteeKeywordRepository extends JpaRepository<MenteeKeywordEntity, Long> {

    // writingId 리스트에 포함된 모든 키워드 엔티티 조회
    List<MenteeKeywordEntity> findAllByMentee_WritingIdIn(List<Long> writingIds);
}
