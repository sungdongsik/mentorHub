package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.UserCreateRequest;
import com.mentorHub.api.dto.response.UserResponse;
import com.mentorHub.api.entity.UserEntity;
import com.mentorHub.api.service.UserService;
import com.mentorHub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> setUsers(@RequestBody UserCreateRequest request){
        log.info("request: {}", request);
        UserEntity en = userService.setUsers(request.toEntity());

        return ApiResponse.success(UserResponse.from(en));
    }

}
