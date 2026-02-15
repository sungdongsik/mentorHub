package com.mentorHub.api.service;

import com.mentorHub.api.dto.response.RootKeywordCandidateResponse;
import com.mentorHub.api.dto.response.RootKeywordResponse;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import com.mentorHub.api.vector.VectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class KeywordApprovalFacade {

    private final MenteeService menteeService;

    private final RootKeywordService rootKeywordService;

    private final VectorService vectorService;

    private final MenteeVectorAssemblerService menteeVectorAssemblerService;

    public List<RootKeywordResponse> getKeywordApproval(RootKeywordEntity request) {
        List<RootKeywordAliasEntity> aliases = rootKeywordService.getKeywordApproval(request);

        if (aliases.isEmpty()) {
            return List.of();
        }

        // 성능 최적화: 전체 RootKeyword를 한 번에 조회하여 메모리에서 매칭 (N+1 문제 해결)
        List<RootKeywordEntity> allRoots = rootKeywordService.getKeywordActive();

        return aliases.stream()
                .map(alias -> {
                    String aliasLower = alias.getAliasName().toLowerCase(Locale.ROOT);

                    List<RootKeywordCandidateResponse> candidates = allRoots.stream()
                            .filter(root ->
                                    root.getCanonicalName()
                                            .toLowerCase(Locale.ROOT)
                                            .contains(aliasLower)
                            )
                            .map(root -> RootKeywordCandidateResponse.builder()
                                    .rootKeywordId(root.getRootKeywordId())
                                    .canonicalName(root.getCanonicalName())
                                    .score(score(alias.getAliasName(), root.getCanonicalName()))
                                    .build())
                            .toList();

                    return RootKeywordResponse.from(alias, candidates);
                })
                .toList();
    }

    public RootKeywordEntity pubKeywordApproval(RootKeywordEntity request) {

        // PENDING -> ACTIVE 업데이트 해주기
        RootKeywordEntity en = rootKeywordService.pubKeywordApproval(request);

        // 해당 루트 키워드를 사용 중인 멘티들을 조회
        List<MenteeEntity> mentees = menteeService.findAllByKeywordApproval(en);

        // 각 멘티의 정보를 벡터 DB에 다시 반영
        List<Document> docs = mentees.stream()
                .map(menteeVectorAssemblerService::assemble)
                .toList();

        vectorService.saveAll(docs);

        return en;
    }

    float score(String alias, String canonical) {
        alias = alias.toLowerCase(Locale.ROOT);
        canonical = canonical.toLowerCase(Locale.ROOT);

        if (!canonical.contains(alias)) {
            return 0.0f;
        }

        return (float) alias.length() / canonical.length();
    }
}
