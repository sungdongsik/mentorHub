package com.util;

public enum IntentType {
    MENTEE_SEARCH(000000),
    CHAT(000001)

    ;

    private final int code;

    IntentType(int code) {
        this.code = code;
    }
}
