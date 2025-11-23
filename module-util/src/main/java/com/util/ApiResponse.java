package com.util;

import lombok.Builder;
import lombok.Getter;

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
                .status(HttpStatusCode.SUCCESS.getCode())
                .message(HttpStatusCode.SUCCESS.name())
                .data(data)
                .build();
    }
}
