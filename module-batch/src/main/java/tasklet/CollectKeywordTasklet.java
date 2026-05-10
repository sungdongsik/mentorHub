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

        jobContext.put("emailContent", emailContent);
        jobContext.put("pendingCount", pendingAliases.size());

        log.info("수집된 PENDING 키워드 수: {}", pendingAliases.size());

        return RepeatStatus.FINISHED;
    }
}
