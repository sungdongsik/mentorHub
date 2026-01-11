package com.util;

public enum MenteeRecruitmentStatus {
    RECRUITING(000000), // 모집중
    CLOSED(000001)      // 마감
    ;

    private final int code;

    MenteeRecruitmentStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
