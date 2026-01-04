package com.message;

import lombok.Getter;

@Getter
public enum ChatDefaultMessage {
    MENTEE_RECOMMENDATION("너는 개발 멘티 추천을 도와주는 AI야. 답변은 간결하고 명확하게 해. 출력은 반드시 한국어로 해."),

    INTENT_CLASSIFIER(
            "당신은 의도 분류자입니다. 반드시 JSON만 반환하세요. " +
                    "intent 필드는 MENTEE_SEARCH 또는 CHAT 중 하나의 값을 가집니다. " +
                    "skills 필드는 문자열 배열(JSON array)로만 반환하세요. " +
                    "skills가 1개여도 [\"java\"] 처럼 배열 형태로 반환해야 합니다. " +
                    "예시: { \"intent\": \"MENTEE_SEARCH\", \"skills\": [\"java\", \"spring\"] } " +
                    "User: %s"
    ),
    NO_SKILL_DETECTED("검색할 키워드가 감지되지 않았어요 😅\n찾으려는 기술 스택을 알려주세요!"),

    NO_MENTEE_FOUND("죄송해요. 해당 키워드를 가진 멘티를 찾지 못했어요 😭\n다른 키워드로 다시 시도해볼까요?"),

    SYSTEM_ERROR("죄송합니다. 요청을 처리하는 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");

    ;

    private final String message;

    ChatDefaultMessage(String message) {
        this.message = message;
    }

    public String format(String format) {
        return message.formatted(format);
    }

}
