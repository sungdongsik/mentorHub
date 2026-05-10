package job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import tasklet.CollectKeywordTasklet;
import tasklet.SendKeywordEmailTasklet;

@Configuration
@RequiredArgsConstructor
public class PendingKeywordEmailJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CollectKeywordTasklet collectKeywordTasklet;
    private final SendKeywordEmailTasklet sendKeywordEmailTasklet;
    private final EmailJobExecutionListener emailJobExecutionListener;

    @Bean
    public Job pendingKeywordEmailJob() {
        return new JobBuilder("pendingKeywordEmailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(emailJobExecutionListener)
                .start(collectKeywordStep())
                .next(sendKeywordEmailStep())
                .build();
    }

    // Step 1: PENDING 키워드를 Chunk로 읽어 Context에 누적
    @Bean
    public Step collectKeywordStep() {
        return new StepBuilder("collectKeywordStep", jobRepository)
                .tasklet(collectKeywordTasklet, transactionManager)
                .build();
    }

    // Step 2: Context에서 꺼내 한 통의 메일로 발송
    @Bean
    public Step sendKeywordEmailStep() {
        return new StepBuilder("sendKeywordEmailStep", jobRepository)
                .tasklet(sendKeywordEmailTasklet, transactionManager)
                .build();
    }
}
