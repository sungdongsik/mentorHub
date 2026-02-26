package com.mentorHub.api.scheduler;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.service.EmailService;
import com.mentorHub.api.service.RootKeywordService;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootKeywordScheduler {

    private final RootKeywordService rootKeywordService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    // 매일 새벽 3시에 실행 (초 분 시 일 월 요일)
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional(readOnly = true)
    public void sendPendingKeywordsByEmail() {
        log.info("승인 대기 중인 키워드 이메일 발송 스케줄러 시작...");

        List<RootKeywordAliasEntity> pendingAliases = rootKeywordService.getKeywordApproval(RootKeywordAliasStatus.PENDING);

        if (!pendingAliases.isEmpty()) {
            String emailContent = pendingAliases.stream()
                    .map(alias -> "키워드명: " + alias.getAliasName() + ", 상태: " + alias.getStatus().getName())
                    .collect(Collectors.joining("\n"));

            emailService.sendSimpleMessage(adminEmail, "승인 대기 중인 키워드 목록", emailContent);
            log.info("이메일 발송 완료. 총 {}개의 대기 중인 키워드가 전송되었습니다.", pendingAliases.size());
        } else {
            log.info("전송할 대기 중인 키워드가 없습니다.");
        }
    }
}
