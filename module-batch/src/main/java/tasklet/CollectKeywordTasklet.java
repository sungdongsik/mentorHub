package tasklet;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.service.RootKeywordService;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectKeywordTasklet implements Tasklet {

    private final RootKeywordService rootKeywordService;

    /**
     * Step 1: PENDING 상태의 키워드를 DB에서 조회하여 JobExecutionContext에 저장합니다.
     *
     * 이 Tasklet은 실제 메일을 발송하지 않고 데이터 수집만 담당합니다.
     * 수집한 데이터는 JobExecutionContext를 통해 다음 Step(SendKeywordEmailTasklet)과
     * Job 종료 후 실행되는 EmailJobExecutionListener에서 공유하여 사용합니다.
     *
     * [전체 흐름]
     * CollectKeywordTasklet (수집) → SendKeywordEmailTasklet (발송) → EmailJobExecutionListener (이력 저장)
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        log.info("PENDING 키워드 수집 시작");

        List<RootKeywordAliasEntity> pendingAliases = rootKeywordService.getKeywordApproval(RootKeywordAliasStatus.PENDING);

        String emailContent = pendingAliases.stream()
                .map(alias -> "키워드명: " + alias.getAliasName())
                .collect(Collectors.joining("\n"));

        // Job ExecutionContext에 저장 (다음 Step과 Listener에서 공유)
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        // 다음 Step(발송)과 Listener(이력 저장)에서 사용할 데이터를 Context에 저장
        jobContext.put("emailContent", emailContent);
        jobContext.put("pendingCount", pendingAliases.size());

        log.info("수집된 PENDING 키워드 수: {}", pendingAliases.size());

        // FINISHED: 이 Tasklet을 한 번만 실행하고 다음 Step으로 넘어감
        // (CONTINUABLE을 반환하면 RepeatStatus가 FINISHED가 될 때까지 반복 실행됨)
        return RepeatStatus.FINISHED;
    }
}
