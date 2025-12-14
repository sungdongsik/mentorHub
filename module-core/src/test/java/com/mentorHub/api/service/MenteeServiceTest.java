package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.MenteeCreateRequest;
import com.mentorHub.api.dto.request.MenteeDeleteRequest;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.repository.MenteeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MenteeServiceTest {

    // 테스트 대상
    @InjectMocks
    private MenteeShipService menteeShipService;

    @Mock
    private MenteeRepository menteeRepository;

    @Test
    @DisplayName("멘티 글 등록하기")
    void setMenteesTest() {
        // 더미 데이터 생성
        Long writingId = 10L;
        String title = "멘토링 신청합니다!";
        String content = "내용~~";

        // 객체 정의해주기
        MenteeCreateRequest request = new MenteeCreateRequest();
        request.setTitle(title);
        request.setContent(content);

        MenteeEntity en = MenteeEntity
                .builder()
                .writingId(writingId)
                .title(title)
                .content(content)
                .build();

        // 가짜 행동 정의해주기
        given(menteeRepository.save(any(com.mentorHub.api.entity.MenteeEntity.class)))
                .willReturn(en);

        // 실행하기
        MenteeEntity response = menteeShipService.setMentees(request.toEntity());

        // 검증하기
        assertThat(response.getWritingId()).isEqualTo(writingId);
        assertThat(response.getTitle()).isEqualTo(title);

        // 2. Repository의 save() 메서드가 실제로 1번 호출되었는지 검증
        verify(menteeRepository, times(1)).save(any(MenteeEntity.class));
    }


    @Test
    @DisplayName("멘티 글 삭제하기")
    void deleteMenteesTest() {
        Long writingId = 20L;
        MenteeDeleteRequest request = new MenteeDeleteRequest();

        MenteeEntity result = menteeShipService.deleteMentees(request.toEntity());

        // 검증
        verify(menteeRepository, times(1)).deleteById(writingId);

        // 2. 반환된 응답의 필드 검증
        assertThat(result.getWritingId()).isEqualTo(writingId);
        assertThat(result.getTitle()).isNull();
    }


}