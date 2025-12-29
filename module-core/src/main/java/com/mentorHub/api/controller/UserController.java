package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.UserCreateRequest;
import com.mentorHub.api.dto.request.UserPutRequest;
import com.mentorHub.api.dto.response.UserCreateResponse;
import com.mentorHub.api.dto.response.UserPutResponse;
import com.mentorHub.api.entity.UserEntity;
import com.mentorHub.api.service.UserService;
import com.mentorHub.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserCreateResponse> setUsers(@Valid @RequestBody UserCreateRequest request){
        log.info("request: {}", request);
        UserEntity en = userService.setUsers(request.toEntity());

        return ApiResponse.success(UserCreateResponse.from(en));
    }

    @PutMapping("/me")
    public ApiResponse<UserPutResponse> putUsers(@Valid @RequestBody UserPutRequest request) {
        log.info("request: {}", request);
        UserEntity en = userService.putUsers(request.toEntity());

        return ApiResponse.success(UserPutResponse.from(en));
    }
}
