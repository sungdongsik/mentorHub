package com.mentorHub.api.service;

import com.mentorHub.api.dto.IntentResponse;
import com.mentorHub.api.dto.request.ChatMessageCreateRequest;
import com.mentorHub.api.dto.response.ChatMessageResponse;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.repository.MenteeRepository;
import com.message.ChatDefaultMessage;
import com.util.IntentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenteeChatFacadeTest {

    @InjectMocks
    private MenteeChatFacade menteeChatFacade;   // 실제 객체

    @Mock
    private MenteeService menteeService;

    @Mock
    private GeminiService geminiService;

    @Mock
    private MenteeRepository menteeRepository;

    @Test
    void 멘티_메시지_성공() {
        // 테스트용 요청 객체 생성
        ChatMessageCreateRequest request = ChatMessageCreateRequest.builder()
                .content("자바 멘티 찾아줘")
                .build();

        // Gemini가 분류해서 내려줄 응답(Mock)
        IntentResponse intent = IntentResponse.builder()
                .intent(IntentType.MENTEE_SEARCH) // 의도 = 멘티 검색
                .skills(List.of("java"))          // 분류된 스킬 = java
                .build();

        // geminiService.classify()가 호출되면 intent를 반환하도록 Mock 설정
        when(geminiService.classify("자바 멘티 찾아줘")).thenReturn(intent);

        // 테스트용 멘티 리스트를 직접 생성한다(테스트하기 위해서 전체 조회를 했지만 다음엔 3 ~ 5명 필터 걸어서 조회하기)
        List<MenteeEntity> mentees = menteeRepository.findAll();

        // menteeService.findByKeywords() 호출 시 위 데이터 반환하도록 Mock 설정
        when(menteeService.findByKeywords(List.of("java"))).thenReturn(mentees);

        // 실제 테스트 대상 메서드 실행
        ChatMessageResponse response = menteeChatFacade.sendMessage(request);

        // Gemini가 실제로 classify를 호출했는지 확인
        verify(geminiService).classify("자바 멘티 찾아줘");

        // menteeService.findByKeywords()도 호출되었는지 확인
        verify(menteeService).findByKeywords(List.of("java"));

        // 응답이 null 아니어야 함
        assertThat(response).isNotNull();

        // 응답 안에 "홍길동" 문자열이 포함되어야 함
        assertThat(response.getContent()).contains("홍길동");
    }

    @Test
    void 스킬이_없으면_NO_SKILL_DETECTED_메시지를_반환() {

        // given
        // 사용자가 입력한 채팅 메시지 생성
        ChatMessageCreateRequest request = ChatMessageCreateRequest.builder()
                .content("GO 멘티 찾아줘")
                .build();

        // Gemini가 분류해서 내려줄 응답(Mock)
        IntentResponse intent = IntentResponse.builder()
                .intent(IntentType.MENTEE_SEARCH) // 의도 = 멘티 검색
                .skills(List.of("GO"))          // 분류된 스킬 = GO
                .build();

        // classify() 호출 시 위 IntentResponse 를 반환하도록 Mock 설정
        when(geminiService.classify(anyString())).thenReturn(intent);

        // when
        // 실제 Facade 메서드를 호출 → 흐름 검증 시작
        ChatMessageResponse response = menteeChatFacade.sendMessage(request);

        // then
        // 스킬이 없으면(findByKeywords가 호출되면 안 됨)
        verify(menteeService, never()).findByKeywords(anyList());

        // 응답 메시지가 "스킬을 찾을 수 없습니다" 기본 메시지인지 검증
        assertThat(response.getContent()).isEqualTo(ChatDefaultMessage.NO_SKILL_DETECTED.getMessage());
    }

    @Test
    void 멘티_해당_되지_않는_일반_CHAT_메시지를_반환() {

        // given
        // 사용자가 일반 대화를 입력했다고 가정
        ChatMessageCreateRequest request = ChatMessageCreateRequest.builder()
                .content("오늘 날씨 어때?")
                .build();

        // Intent 분류 결과 → CHAT 으로 반환하도록 Mock 설정
        IntentResponse intent = IntentResponse.builder()
                .intent(IntentType.CHAT)  // ⭐ 일반 대화 의도
                .skills(List.of())        // 스킬 없음
                .build();

        when(geminiService.classify("오늘 날씨 어때?")).thenReturn(intent);

        // Gemini 챗봇의 응답도 Mock 으로 정의
        when(geminiService.geminiChat("오늘 날씨 어때?")).thenReturn("오늘 날씨는 맑아요!");

        // when
        ChatMessageResponse response = menteeChatFacade.sendMessage(request);

        // then
        // classify 는 호출되어야 하고
        verify(geminiService).classify("오늘 날씨 어때?");

        // 멘티 검색은 호출되면 안 됨
        verify(menteeService, never()).findByKeywords(anyList());

        // geminiChat 은 반드시 호출되어야 함
        verify(geminiService).geminiChat("오늘 날씨 어때?");

        // 응답 검증
        assertThat(response.getContent()).isEqualTo("오늘 날씨는 맑아요!");
    }

}