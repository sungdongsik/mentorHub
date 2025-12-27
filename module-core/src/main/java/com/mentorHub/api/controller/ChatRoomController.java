package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.ChatRoomCreateRequest;
import com.mentorHub.api.dto.request.ChatRoomDeleteRequest;
import com.mentorHub.api.dto.request.ChatRoomMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatRoomCommandResponse;
import com.mentorHub.api.dto.response.ChatRoomMessageResponse;
import com.mentorHub.api.entity.ChatRoomEntity;
import com.mentorHub.api.entity.ChatRoomMessageEntity;
import com.mentorHub.api.service.ChatRoomService;
import com.mentorHub.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public void getChatRoom() {

    }

    @PostMapping
    public ApiResponse<ChatRoomCommandResponse> setChatRoom(@Valid @RequestBody ChatRoomCreateRequest request) {
        log.info("request: {}", request);
        ChatRoomEntity en = chatRoomService.setChatRoom(request.toEntity());

        return ApiResponse.success(ChatRoomCommandResponse.from(en));
    }

    @DeleteMapping
    public ApiResponse<ChatRoomCommandResponse> deleteChatRoom(@RequestBody ChatRoomDeleteRequest request) {
        log.info("request: {}", request);
        ChatRoomEntity en = chatRoomService.deleteChatRoom(request.toEntity());

        return ApiResponse.success(ChatRoomCommandResponse.from(en));
    }

    @PostMapping("/messages")
    public ApiResponse<ChatRoomMessageResponse> setMessage(@Valid @RequestBody ChatRoomMessageCreateRequest request) {
        log.info("request: {}", request);
        ChatRoomMessageEntity en = chatRoomService.setMessage(request.toEntity(chatRoomService.findById(request.getChatId())));
        return null;
    }
}
