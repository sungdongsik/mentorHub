package com.mentorHub.api.service;

import com.mentorHub.api.entity.EmailVerificationEntity;
import com.mentorHub.api.repository.EmailVerificationRepository;
import com.util.CommonStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;
    private final EmailHistoryService emailHistoryService;

    /**
     * 이메일 인증 코드를 생성하고 전송합니다.
     * @param request 수신할 이메일 주소
     */
    public EmailVerificationEntity sendVerificationCode(EmailVerificationEntity request) {
        String code = generateCode();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5); // 5분 유효

        EmailVerificationEntity verification = EmailVerificationEntity.builder()
                .email(request.getEmail())
                .code(code)
                .expiryDate(expiryDate)
                .isSuccess(CommonStatus.FAIL)
                .build();

        // 인증 데이터 저장
        EmailVerificationEntity en = emailVerificationRepository.save(verification);

        // 메일 전송 로직
        String subject = "[MentorHub] 이메일 인증 번호 안내";
        String content = "인증 번호: " + code;
        CommonStatus success = emailService.sendSimpleMessage(request.getEmail(), subject, content);
        
        // 메일 발송 이력 저장
        emailHistoryService.saveEmailHistory(request.getEmail(), subject, content, success);

        if (success == CommonStatus.FAIL) {
            throw new RuntimeException("이메일 발송에 실패했습니다.");
        }

        return en;
    }

    /**
     * 입력받은 인증 코드를 검증합니다.
     * @param request 검증할 이메일 주소, 사용자가 입력한 인증 코드
     * @return 검증 성공 여부
     */
    public EmailVerificationEntity verifyCode(EmailVerificationEntity request) {
        // 해당 이메일로 발송된 가장 최근 인증 정보를 조회
        EmailVerificationEntity en = emailVerificationRepository.findTopByEmailOrderByCreatedDateDesc(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("인증 정보를 찾을 수 없습니다."));

        // 만료 여부 확인
        if (en.isExpired()) {
            throw new IllegalArgumentException("인증 번호가 만료되었습니다.");
        }

        // 인증 코드 일치 여부 확인
        if (!en.getCode().equals(request.getCode())) {
            throw new IllegalArgumentException("인증 번호가 일치하지 않습니다.");
        }

        // 인증 완료 처리
        en.verify();
        return en;
    }

    /**
     * 6자리 숫자형 인증 코드를 생성합니다.
     * @return 생성된 6자리 코드 문자열
     */
    private String generateCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
