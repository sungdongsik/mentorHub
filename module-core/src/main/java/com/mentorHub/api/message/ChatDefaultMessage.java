package com.mentorHub.api.message;

import lombok.Getter;
import org.springframework.ai.document.Document;

import java.util.List;

@Getter
public enum ChatDefaultMessage {

    INTENT_CLASSIFIER(
            """
            당신은 멘티들의 정보를 잘 알고 있는 전문 상담가입니다. 
            아래 제공된 [멘티 정보]를 바탕으로 사용자의 질문에 친절하게 답변해 주세요.
            만약 제공된 정보에 답이 없다면, 모른다고 답하기보다는 아는 범위 내에서 조언해 주세요.

            [멘티 정보]
            %s

            [사용자 질문]
            %s
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
