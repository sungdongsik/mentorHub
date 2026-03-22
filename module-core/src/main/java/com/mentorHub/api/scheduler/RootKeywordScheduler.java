package com.mentorHub.api.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootKeywordScheduler {

    private final DailyTask dailyTasks;

    // 매일 새벽 3시에 실행 (초 분 시 일 월 요일)
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void executeDailyTasks() {
        log.info("일일 작업 스케줄러 시작...");
        try {
            dailyTasks.execute();
        } catch (Exception e) {
            log.error("작업 실행 중 오류 발생: {}", dailyTasks.getClass().getSimpleName(), e);
        }
        log.info("일일 작업 스케줄러 종료.");
    }
}
