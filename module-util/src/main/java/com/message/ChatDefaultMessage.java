package com.message;

import lombok.Getter;

@Getter
public enum ChatDefaultMessage {
    MENTEE_RECOMMENDATION("너는 개발 멘티 추천을 도와주는 AI야. 답변은 간결하고 명확하게 해. 출력은 반드시 한국어로 해."),

    INTENT_CLASSIFIER("당신은 의도 분류자입니다. JSON만 반환합니다. { \"intent\": \"MENTEE_SEARCH\" or \"CHAT\", \"skills\": [\"keyword1\"] } User: %s")
    ;

    private final String message;

    ChatDefaultMessage(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return message.formatted(args);
    }

}
