package com.mentorHub.api.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Spring Batch Job을 실행하기 위한 스케줄러 클래스입니다.
 * @Scheduled 어노테이션을 사용하여 정해진 주기에 맞게 배치를 실행합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RootKeywordScheduler {

    private final JobLauncher jobLauncher;
    private final Job pendingKeywordEmailJob;

    /**
     * 매일 새벽 3시에 실행되도록 설정된 스케줄러입니다.
     * (Cron: 초 분 시 일 월 요일)
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void executeDailyTasks() {
        log.info("일일 작업 스케줄러 시작...");
        try {
            /**
             * Spring Batch의 Job은 동일한 JobParameters로 두 번 실행할 수 없습니다.
             * (이미 성공한 JobInstance가 존재하기 때문)
             * 매 실행마다 고유한 파라미터(현재 시간)를 추가하여 새로운 JobInstance를 생성합니다.
             */
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("datetime", LocalDateTime.now().toString())
                    .toJobParameters();

            // 정의된 배치 Job을 실행합니다.
            jobLauncher.run(pendingKeywordEmailJob, jobParameters);
        } catch (Exception e) {
            log.error("배치 작업 실행 중 오류 발생", e);
        }
        log.info("일일 작업 스케줄러 종료.");
    }
}
