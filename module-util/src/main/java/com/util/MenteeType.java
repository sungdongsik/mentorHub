package com.util;

public enum MenteeType {
    MENTEE(000000),
    MENTO(000001)
;
    private final int code;

    MenteeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
