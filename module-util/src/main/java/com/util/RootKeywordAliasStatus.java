package com.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RootKeywordAliasStatus {
    PENDING(000000, "대기"),     // 자동 등록된 임시 키워드 (관리자 승인 대기 상태)
    ACTIVE(000001, "활성"),      // 승인 완료된 정상 사용 키워드
    MERGED(000002, "병합"),      // 다른 키워드로 병합됨 (실제 사용은 mergedTo 키워드)
    DEPRECATED(000003, "만료"),  // 기존 데이터는 유지하되 신규 사용 중단된 키워드
    DELETED(000004, "삭제")     // 삭제된 키워드 (시스템상 참조 불가)
    ;

    private final int code;
    private final String name;

    private static final Map<Integer, RootKeywordAliasStatus> BY_CODE =
            Arrays.stream(values()).collect(Collectors.toMap(RootKeywordAliasStatus::getCode, Function.identity()));

    RootKeywordAliasStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RootKeywordAliasStatus findByCode(int code) {
        return BY_CODE.get(code);
    }
}
