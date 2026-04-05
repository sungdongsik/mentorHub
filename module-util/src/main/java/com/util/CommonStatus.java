package com.util;

public enum CommonStatus {
    SUCCESS(000000),
    FAIL(000001)
    ;

    private final int code;

    CommonStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
