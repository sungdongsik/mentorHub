package com.mentorHub.api.repository;

import com.mentorHub.api.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, Long> {
}
