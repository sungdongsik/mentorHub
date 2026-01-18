package com.mentorHub.api.message;

import lombok.Getter;

@Getter
public enum ChatDefaultMessage {

    INTENT_CLASSIFIER(
            """
            너는 멘티 추천 서비스의 AI 어시스턴트다.
    
            아래 절차를 반드시 순서대로 수행하라.
    
            1. 사용자의 질문 의도를 판단한다.
               - MENTEE_SEARCH : 기술, 스킬, 조건 등을 기반으로 멘티를 찾으려는 질문
               - CHAT    : 일반 대화, 인사, 잡담, 단순 질문
    
            2. 의도가 MENTEE_SEARCH인 경우에만 질문에서 핵심 키워드를 추출한다.
               - 기술명, 언어, 프레임워크, 직무, 관심 분야 위주로 추출한다.
               - CHAT인 경우 MENTEE_SEARCH는 빈 배열로 반환한다.
    
            3. 사용자에게 보여줄 최종 응답 메시지를 생성한다.
    
            ⚠️ 반드시 아래 JSON 형식으로만 응답해야 한다.
            ⚠️ JSON 외의 어떤 문장도 절대 포함하지 마라.
    
            {
              "intent": "MENTEE_SEARCH" | "CHAT",
              "keywords": ["keyword1", "keyword2"],
              "answer": "사용자에게 보여줄 최종 응답"
            }
    
            사용자 질문: %s
            """
    );

    private final String message;

    ChatDefaultMessage(String message) {
        this.message = message;
    }

    public String format(String format) {
        return message.formatted(format);
    }

}
