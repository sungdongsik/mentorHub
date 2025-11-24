package com.util;

/**
 * 25.11.20
 * 성공	200	SUCCESS
 * 잘못된 요청	400	BAD REQUEST
 * 인증 실패	401	UNAUTHORIZED
 * 데이터 없음	404	NOT FOUND
 * 서버 오류	500	INTERNAL SERVER ERROR
 */

public enum HttpStatusCode {
    SUCCESS(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
