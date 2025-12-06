package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.MenteeApplicationCreateRequest;
import com.mentorHub.api.dto.response.MenteeApplicationResponse;
import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.util.ApplicationStausType;
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
public class MenteeApplicationServiceTest {

    @InjectMocks
    private MenteeService menteeApplicationService;

    @Mock
    private MenteeApplicationRepository menteeApplicationRepository;

    @Test
    @DisplayName("멘티 지원서 등록")
    void createMenteesApplicationTest() {
        // Given (준비)
        // 1. 더미 데이터 및 요청 객체 생성
        Long menteeId = 5L;
        ApplicationStausType admission = ApplicationStausType.APPLICATION;
        MenteeApplicationCreateRequest request = new MenteeApplicationCreateRequest();

        // 2. Repository가 저장 후 반환할 가짜 Entity 정의
        MenteeApplicationEntity mockSavedEntity = MenteeApplicationEntity.builder()
                .menteeId(menteeId)
                .admission(admission)
                .build();

        // 3. 가짜 행동 정의
        given(menteeApplicationRepository.save(any(MenteeApplicationEntity.class)))
                .willReturn(mockSavedEntity);

        // 실행
        MenteeApplicationEntity response = menteeApplicationService.createMenteesApplication(request.toEntity());

        // 반환된 응답의 필드 검증
        assertThat(response.getMenteeId()).isEqualTo(menteeId);
        assertThat(response.getAdmission()).isEqualTo(admission);

        verify(menteeApplicationRepository, times(1)).save(any(MenteeApplicationEntity.class));
    }
}
