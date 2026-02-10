package com.mentorHub.api.scheduler;

import com.mentorHub.api.repository.RootKeywordRepository;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootKeywordScheduler {

    private final RootKeywordRepository rootKeywordRepository;

    // 매일 새벽 3시에 실행 (초 분 시 일 월 요일)
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void updateRootKeywordStatus() {
        log.info("Starting RootKeyword status update scheduler...");

        int updatedCount = rootKeywordRepository.bulkUpdateStatus(RootKeywordAliasStatus.PENDING, RootKeywordAliasStatus.ACTIVE);

        if (updatedCount > 0) {
            log.info("Completed RootKeyword status update. Total updated: {}", updatedCount);
        } else {
            log.info("No pending keywords to update.");
        }
    }
}