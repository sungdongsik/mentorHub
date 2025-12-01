package com.util;

public enum UserType {
    MENTEE(000000),
    MENTO(000001)
;
    private final int code;

    UserType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
