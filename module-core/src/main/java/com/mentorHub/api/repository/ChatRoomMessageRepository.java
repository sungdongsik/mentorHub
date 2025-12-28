package com.mentorHub.api.repository;

import com.mentorHub.api.entity.ChatRoomMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMessageRepository extends JpaRepository<ChatRoomMessageEntity, Long> {
}
