package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.dto.response.MenteeKeywordResponse;
import com.mentorHub.api.entity.MenteeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenteeChatFacadeTest {

    @InjectMocks
    private MenteeChatFacade menteeChatFacade;

    @Mock
    private MenteeService menteeService;

    @Mock
    private GeminiService geminiService;

    @Test
    @DisplayName("멘티 추천 응답 받기")
    void sendMessage() {

        // 사용자가 채팅으로 입력한 메시지
        ChatMessageCreateRequest request = ChatMessageCreateRequest.builder()
                .content("java, python 멘티 찾아줘")
                .build();

        // 실제 DB를 조회하지 않고 이 데이터가 반환되도록 Mock 설정
        List<MenteeKeywordResponse> menteeKeywords = List.of(
                new MenteeKeywordResponse("홍길동", List.of("Java", "Python")),
                new MenteeKeywordResponse("김철수", List.of("HTML", "CSS"))
        );

        when(menteeService.findTopWithKeywords())
                .thenReturn(menteeKeywords);

        String geminiAnswer =
                """
                1. 홍길동님 – 자바, 파이썬
                2. 김철수님 – HTML, CSS
                """;

        // 실제 API 호출 대신 위 문자열을 돌려주도록 설정
        when(geminiService.classify(menteeKeywords, request.getContent()))
                .thenReturn(geminiAnswer);

        // when
        ChatMessageResponse response = menteeChatFacade.sendMessage(request);

        // then
        assertThat(response.getContent()).isEqualTo(geminiAnswer);

        // 멘티 목록을 조회했는지 검증
        verify(menteeService).findTopWithKeywords();

        // Gemini를 올바른 파라미터로 호출했는지 검증
        verify(geminiService).classify(menteeKeywords, request.getContent());
    }

    @Test
    @DisplayName("조건에 맞는 멘티가 없으면 실패 문구를 그대로 반환한다")
    void sendMessage_noMentee() {
        // given
        ChatMessageCreateRequest request = ChatMessageCreateRequest.builder()
                .content("쿠버네티스, 러스트 멘티 찾아줘")
                .build();

        List<MenteeKeywordResponse> mentees = List.of(
                new MenteeKeywordResponse("홍길동", List.of("Java", "Spring")),
                new MenteeKeywordResponse("김철수", List.of("HTML", "CSS"))
        );

        when(menteeService.findTopWithKeywords())
                .thenReturn(mentees);

        // ChatDefaultMessage - 규칙 6번에 따라 실패 문구를 반환했다고 가정
        String geminiFailMessage =
                "죄송합니다. 해당 요청에 맞는 멘티를 찾지 못했습니다.";

        // 실제 API 호출 대신 위 문자열을 돌려주도록 설정
        when(geminiService.classify(mentees, request.getContent()))
                .thenReturn(geminiFailMessage);

        // when
        ChatMessageResponse response = menteeChatFacade.sendMessage(request);

        // then
        assertThat(response.getContent()).isEqualTo(geminiFailMessage);

        verify(menteeService).findTopWithKeywords();
        verify(geminiService).classify(mentees, request.getContent());
    }
}