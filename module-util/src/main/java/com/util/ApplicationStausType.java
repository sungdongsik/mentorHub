package com.util;

public enum ApplicationStausType {
    APPLICATION(000010),
    OK(000011),
    CANCEL(000012),
    WAIT(000013),
    ;
    private final int code;

    ApplicationStausType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
