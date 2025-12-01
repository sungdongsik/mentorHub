package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeApplicationRepository extends JpaRepository<MenteeApplicationEntity, Long> {
}
