package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.service.MenteeChatFacade;
import com.mentorHub.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final MenteeChatFacade menteeChatFacade;

    @PostMapping
    public ApiResponse<ChatMessageResponse> sendMessage(@Valid @RequestBody ChatMessageCreateRequest request) {
        log.info("request: {}", request);
        return ApiResponse.success(menteeChatFacade.sendMessage(request));
    }
}
