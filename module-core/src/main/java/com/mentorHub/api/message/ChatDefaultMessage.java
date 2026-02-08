package com.mentorHub.api.message;

import lombok.Getter;
import org.springframework.ai.document.Document;

import java.util.List;

@Getter
public enum ChatDefaultMessage {

    INTENT_CLASSIFIER(
            """
            당신은 멘티들의 글과 이력을 바탕으로 상담을 제공하는 전문 AI 상담가입니다.
        
            아래 제공된 [멘티 정보]는 여러 개일 수 있습니다.
            사용자 질문과 직접적으로 관련 있는 멘티 정보만 선택하여 판단하세요.
            관련 없는 멘티 정보는 반드시 무시하세요.
        
            각 멘티 정보에는 고유한 id(writing_ids)가 포함되어 있습니다.
            답변 시 반드시 판단에 실제로 사용한 멘티 정보의 writing_id를 근거로 사용해야 합니다.
        
            [멘티 정보]
            %s
        
            [사용자 질문]
            %s
        
            반드시 아래 JSON 형식으로만 응답하세요.
            추가 설명, 마크다운, 주석, 자연어 텍스트는 절대 포함하지 마세요.
        
            {
              "type": "MENTEE_SEARCH | CHAT",
              "skills": ["string"],
              "message": "string",
              "references": ["writing:{id}"]
            }
        
            규칙:
            - references에는 판단에 실제로 사용한 멘티 정보의 writing_ids 값만 포함하세요.
            - references 값은 반드시 "writing:{id}" 형식의 문자열이어야 합니다.
            - 판단에 사용하지 않은 멘티 정보의 writing_ids는 포함하지 마세요.
            - skills에는 사용자 질문 해결에 직접적으로 관련된 기술만 포함하세요.
            - 멘티 정보에 답이 명확히 없더라도, 제공된 정보를 바탕으로 현실적인 조언을 하세요.
            """
    );

    private final String message;

    ChatDefaultMessage(String message) {
        this.message = message;
    }

    public String format(List<Document> documents, String format) {
        return message.formatted(documents, format);
    }

}
