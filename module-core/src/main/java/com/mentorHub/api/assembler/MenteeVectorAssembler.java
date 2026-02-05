package com.mentorHub.api.assembler;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import com.util.RootKeywordAliasStatus;
import com.util.VectorIdUtil;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MenteeVectorAssembler implements VectorDocumentAssembler<MenteeEntity> {

    @Override
    public Document assemble(MenteeEntity en) {
        // 승인된(ACTIVE) 키워드들만 필터링해서 문자열로 합침
        String approvedKeywords = en.getKeywords().stream()
                .filter(mk -> RootKeywordAliasStatus.ACTIVE.equals(mk.getRootKeyword().getStatus())) // RootKeyword 상태 체크
                .map(MenteeKeywordEntity::getKeyword)
                .collect(Collectors.joining(", "));

        return new Document(
                VectorIdUtil.createId(en.getWritingId()), // ID
                en.getName() + "," + approvedKeywords, // 벡터화할 텍스트 내용
                Map.of("writing_id", en.getWritingId())
        );
    }
}
