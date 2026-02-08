package com.util;

public enum ChatRoleType {
    MENTEE_SEARCH(000000),
    CHAT(000001)
    ;

    private final int code;


    ChatRoleType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
