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

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        int pendingCount = jobContext.getInt("pendingCount", 0);

        if (pendingCount == 0) {
            log.info("전송 대상 없음 - 메일 발송 생략");
            return RepeatStatus.FINISHED;
        }

        String subject = "승인 대기 중인 키워드 목록";
        String content = (String) jobContext.get("emailContent");

        log.info("메일 발송 시작 - 대상 키워드 수: {}", pendingCount);

        // @Retryable이 EmailService에 적용되어 있으므로 단순 호출
        emailService.sendSimpleMessage(adminEmail, subject, content);

        log.info("메일 발송 완료");

        return RepeatStatus.FINISHED;
    }
}
