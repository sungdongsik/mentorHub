package com.mentorHub.api.repository;

import com.mentorHub.api.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    @Modifying
    @Query("update ChatRoomEntity c set c.delYn = 'Y' where c.chatId = :chatId and c.delYn = 'N'")
    int deleteChatRoom(@Param("chatId") Long chatId);
}
