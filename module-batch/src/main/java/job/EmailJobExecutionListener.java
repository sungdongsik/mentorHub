package job;

import com.mentorHub.api.service.EmailHistoryService;
import com.util.CommonStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailJobExecutionListener implements JobExecutionListener {
    private final EmailHistoryService emailHistoryService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public void afterJob(JobExecution jobExecution) {
        boolean isSuccess = jobExecution.getStatus() == BatchStatus.COMPLETED;
        CommonStatus result = isSuccess ? CommonStatus.SUCCESS : CommonStatus.FAIL;

        String subject = "승인 대기 중인 키워드 목록";
        String content = (String) jobExecution.getExecutionContext().get("emailContent");

        log.info("배치 Job 종료 - 상태: {}", jobExecution.getStatus());

        emailHistoryService.saveEmailHistory(adminEmail, subject, content, result);
    }

}
