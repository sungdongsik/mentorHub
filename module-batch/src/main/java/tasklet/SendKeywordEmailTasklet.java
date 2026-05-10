package tasklet;

import com.mentorHub.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendKeywordEmailTasklet implements Tasklet {

    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    /**
     * Step 2: CollectKeywordTasklet에서 수집한 데이터를 꺼내 관리자에게 메일을 발송합니다.
     *
     * 재시도 로직은 이 클래스가 아닌 EmailService.sendSimpleMessage()의
     * @Retryable이 담당합니다. (최대 3회, 5초 간격)
     * 3회 모두 실패 시 MailException이 발생하고 Job은 FAILED 상태로 종료됩니다.
     *
     * [전체 흐름]
     * CollectKeywordTasklet (수집) → SendKeywordEmailTasklet (발송) → EmailJobExecutionListener (이력 저장)
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // Step 1(CollectKeywordTasklet)에서 저장한 데이터를 꺼내기 위해 JobExecutionContext 참조
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        // Step 1에서 저장한 발송 대상 수를 조회. 저장된 값이 없으면 기본값 0 반환
        int pendingCount = jobContext.getInt("pendingCount", 0);

        if (pendingCount == 0) {
            log.info("전송 대상 없음 - 메일 발송 생략");
            return RepeatStatus.FINISHED;
        }

        String subject = "승인 대기 중인 키워드 목록";
        String content = (String) jobContext.get("emailContent");

        log.info("메일 발송 시작 - 대상 키워드 수: {}", pendingCount);

        // EmailService에 @Retryable이 적용되어 있으므로 단순 호출만 하면 됨
        // 발송 실패 시 Spring이 자동으로 최대 3회까지 재시도
        // 3회 모두 실패하면 MailException을 던지고 이 Step은 FAILED로 종료됨
        emailService.sendSimpleMessage(adminEmail, subject, content);

        log.info("메일 발송 완료");

        // FINISHED: 메일 발송 완료, 다음 처리 없이 Step 종료
        return RepeatStatus.FINISHED;
    }
}
