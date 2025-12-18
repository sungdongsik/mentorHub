package com.mentorHub.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 공통 응답
 * @param <T>
 * code: 응답 코드 (예: 200, 400, 500 등)
 * message: 상태 메시지 (예: SUCCESS, FAIL 등)
 * data: 실제 응답 데이터
 */

@Getter
@Builder
public class ApiResponse<T> {
    private int status;

    private String message;

    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> fail(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .data(data)
                .build();
    }
    
}
