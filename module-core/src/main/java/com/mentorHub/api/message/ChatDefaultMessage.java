package com.mentorHub.api.message;

import com.mentorHub.api.dto.response.MenteeKeywordResponse;
import lombok.Getter;

import java.util.List;

@Getter
public enum ChatDefaultMessage {

    INTENT_CLASSIFIER(
            """
            당신은 멘토 추천 어시스턴트입니다.
    
            다음은 우리 서비스의 멘티 목록입니다:
            %s
    
            사용자의 요청:
            "%s"
    
            아래 규칙을 반드시 지키세요.
    
            규칙:
            1. 반드시 위 멘티 목록에 존재하는 멘티만 언급하세요.
            2. 멘티의 keyword와 의미적으로 관련된 요청일 경우에만 추천 대상에 포함하세요.
            3. 목록에 없는 멘티 이름, 키워드, 기술 스택 등을 절대 생성하지 마세요.
            4. 자연스러운 한국어 문장으로만 답변하세요.
            5. JSON, 코드블록, 백틱(`)을 절대 사용하지 마세요.
            6. 규칙을 지킬 수 없다면 "죄송합니다. 해당 요청에 맞는 멘티를 찾지 못했습니다." 라고 답변하세요.
            7. 추천할 경우, 멘티의 '이름'을 반드시 포함해서 답변하세요.
               (이름 없이 설명만 하는 답변은 금지)
            친절하고 읽기 쉽게 한글로만 답변하세요.
            """
    )
    ;

    private final String message;

    ChatDefaultMessage(String message) {
        this.message = message;
    }

    public String format(List<MenteeKeywordResponse> mentees, String format) {
        return message.formatted(mentees, format);
    }

}
