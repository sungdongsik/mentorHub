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

    /**
     * Job이 완전히 종료된 후 실행되는 메서드입니다.
     * 성공/실패 여부와 관계없이 항상 호출됩니다.
     *
     * 역할: 메일 발송 배치 Job의 최종 결과를 이력 테이블에 저장합니다.
     * Tasklet에서 이력 저장을 하지 않는 이유는, Job 전체가 끝난 시점의
     * 최종 상태(성공/실패)를 기록하기 위함입니다.
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        boolean isSuccess = jobExecution.getStatus() == BatchStatus.COMPLETED;
        CommonStatus result = isSuccess ? CommonStatus.SUCCESS : CommonStatus.FAIL;

        String subject = "승인 대기 중인 키워드 목록";

        // CollectKeywordTasklet에서 JobExecutionContext에 저장해둔 메일 본문을 꺼냄
        // Step 간 데이터 공유는 JobExecutionContext를 통해 이루어짐
        String content = (String) jobExecution.getExecutionContext().get("emailContent");

        log.info("배치 Job 종료 - 상태: {}", jobExecution.getStatus());

        // 수신자, 제목, 본문, 성공여부를 이력 테이블에 저장
        // 메일 발송 성공 여부와 무관하게 Job 실행 기록을 남김
        emailHistoryService.saveEmailHistory(adminEmail, subject, content, result);
    }

}
