package com.util;

public enum ChatRoleType {
    USER(000000),
    BOT(000001)
    ;
    private final int code;

    ChatRoleType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
