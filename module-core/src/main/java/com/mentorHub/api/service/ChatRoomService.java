package com.mentorHub.api.service;

import com.mentorHub.api.entity.ChatRoomEntity;
import com.mentorHub.api.entity.ChatRoomMessageEntity;
import com.mentorHub.api.repository.ChatRoomMessageRepository;
import com.mentorHub.api.repository.ChatRoomRepository;
import com.mentorHub.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMessageRepository chatRoomMessageRepository;

    public ChatRoomEntity setChatRoom(ChatRoomEntity request) {
        return chatRoomRepository.save(request);
    }

    public ChatRoomEntity deleteChatRoom(ChatRoomEntity request) {

        int deleteChatRoom = chatRoomRepository.deleteChatRoom(request.getChatId());

        if (deleteChatRoom == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "이미 삭제되었거나 존재하지 않는 봇입니다.");
        }

        return findById(request.getChatId());
    }

    public ChatRoomEntity findById(Long chatId) {
        return chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다!"));
    }

    public ChatRoomMessageEntity setMessage(ChatRoomMessageEntity request) {

        // 사용자가 요청한 메시지 저장하기
        ChatRoomMessageEntity chatRoomMessage = chatRoomMessageRepository.save(request);



        return null;
    }
}
