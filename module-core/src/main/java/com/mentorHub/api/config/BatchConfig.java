package com.mentorHub.api.config;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.service.EmailHistoryService;
import com.mentorHub.api.service.EmailService;
import com.mentorHub.api.service.RootKeywordService;
import com.util.CommonStatus;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final RootKeywordService rootKeywordService;
    private final EmailService emailService;
    private final EmailHistoryService emailHistoryService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Bean
    public Job pendingKeywordEmailJob(JobRepository jobRepository, Step pendingKeywordEmailStep) {
        return new JobBuilder("pendingKeywordEmailJob", jobRepository)
                .start(pendingKeywordEmailStep)
                .build();
    }

    @Bean
    public Step pendingKeywordEmailStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("pendingKeywordEmailStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("승인 대기 중인 키워드 이메일 발송 배치 작업 시작...");

                    List<RootKeywordAliasEntity> pendingAliases = rootKeywordService.getKeywordApproval(RootKeywordAliasStatus.PENDING);

                    if (!pendingAliases.isEmpty()) {
                        String subject = "승인 대기 중인 키워드 목록";
                        String emailContent = pendingAliases.stream()
                                .map(alias -> "키워드명: " + alias.getAliasName() + ", 상태: " + alias.getStatus().getName())
                                .collect(Collectors.joining("\n"));

                        // --- 재시도 로직 적용 ---
                        int maxRetries = 3;
                        int retryCount = 0;
                        CommonStatus isSuccess = CommonStatus.FAIL;

                        while (retryCount < maxRetries) {
                            try {
                                isSuccess = emailService.sendSimpleMessage(adminEmail, subject, emailContent);
                                if (isSuccess == CommonStatus.SUCCESS) break;
                            } catch (Exception e) {
                                log.warn("이메일 발송 시도 {}회 실패: {}", retryCount + 1, e.getMessage());
                            }
                            retryCount++;
                            if (retryCount < maxRetries) {
                                Thread.sleep(1000 * 5); // 5초 대기 후 재시도
                            }
                        }

                        // 결과 저장
                        emailHistoryService.saveEmailHistory(adminEmail, subject, emailContent, isSuccess);

                        if (CommonStatus.SUCCESS == isSuccess) {
                            log.info("이메일 발송 완료.");
                        } else {
                            // !!! 중요: 실패 시 예외를 던져서 배치를 FAILED 상태로 만듭니다.
                            // 이렇게 하면 DB(BATCH_JOB_EXECUTION)에 실패 기록이 남고, 
                            // 다음 스케줄 실행 시 'PENDING' 상태인 데이터들이 다시 처리 대상이 됩니다.
                            throw new RuntimeException("이메일 발송 최종 실패. 총 " + maxRetries + "회 시도함.");
                        }
                    } else {
                        log.info("전송할 대기 중인 키워드가 없습니다.");
                    }

                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
