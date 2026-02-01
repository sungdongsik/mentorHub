package com.util;

public enum RootKeywordAliasStatus {
    PENDING(000000),     // 자동 등록된 임시 키워드 (관리자 승인 대기 상태)
    ACTIVE(000001),      // 승인 완료된 정상 사용 키워드
    MERGED(000002),      // 다른 키워드로 병합됨 (실제 사용은 mergedTo 키워드)
    DEPRECATED(000003),  // 기존 데이터는 유지하되 신규 사용 중단된 키워드
    DELETED(000004)     // 삭제된 키워드 (시스템상 참조 불가)
    ;

    private final int code;

    RootKeywordAliasStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
