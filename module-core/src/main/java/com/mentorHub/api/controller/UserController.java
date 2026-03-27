package com.mentorHub.api.controller;

import com.mentorHub.api.dto.request.EmailVerificationSendRequest;
import com.mentorHub.api.dto.request.EmailVerificationVerifyRequest;
import com.mentorHub.api.dto.request.UserCreateRequest;
import com.mentorHub.api.dto.request.UserPutRequest;
import com.mentorHub.api.dto.response.EmailVerificationSendResponse;
import com.mentorHub.api.dto.response.EmailVerificationVerifyResponse;
import com.mentorHub.api.dto.response.UserCreateResponse;
import com.mentorHub.api.dto.response.UserPutResponse;
import com.mentorHub.api.entity.EmailVerificationEntity;
import com.mentorHub.api.entity.UserEntity;
import com.mentorHub.api.service.EmailVerificationService;
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
    private final EmailVerificationService emailVerificationService;

    /**
     * 회원 가입 API
     * @param request 회원 정보 생성 요청 DTO
     * @return 생성된 유저 정보
     */
    @PostMapping
    public ApiResponse<UserCreateResponse> setUsers(@Valid @RequestBody UserCreateRequest request){
        log.info("request: {}", request);
        UserEntity en = userService.setUsers(request.toEntity());

        return ApiResponse.success(UserCreateResponse.from(en));
    }

    /**
     * 내 정보 수정 API
     * @param request 수정할 유저 정보 요청 DTO
     * @return 수정된 유저 정보
     */
    @PutMapping("/me")
    public ApiResponse<UserPutResponse> putUsers(@Valid @RequestBody UserPutRequest request) {
        log.info("request: {}", request);
        UserEntity en = userService.putUsers(request.toEntity());

        return ApiResponse.success(UserPutResponse.from(en));
    }

    /**
     * 이메일 인증 코드 발송 API
     * @param request 이메일 주소를 포함한 요청 DTO
     * @return 성공 여부
     */
    @PostMapping("/email-verification/send")
    public ApiResponse<EmailVerificationSendResponse> sendVerificationCode(@Valid @RequestBody EmailVerificationSendRequest request) {
        log.info("sendVerificationCode request: {}", request);
        EmailVerificationEntity en = emailVerificationService.sendVerificationCode(request.toEntity());
        return ApiResponse.success(EmailVerificationSendResponse.from(en));
    }

    /**
     * 이메일 인증 코드 검증 API
     * @param request 이메일 주소와 인증 코드를 포함한 요청 DTO
     * @return 인증 성공 여부 (true/false)
     */
    @PostMapping("/email-verification/verify")
    public ApiResponse<EmailVerificationVerifyResponse> verifyCode(@Valid @RequestBody EmailVerificationVerifyRequest request) {
        log.info("verifyCode request: {}", request);
        EmailVerificationEntity en = emailVerificationService.verifyCode(request.toEntity());
        return ApiResponse.success(EmailVerificationVerifyResponse.from(en));
    }
}
