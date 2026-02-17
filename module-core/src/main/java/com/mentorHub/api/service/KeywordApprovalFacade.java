package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.RootKeywordPutRequest;
import com.mentorHub.api.dto.response.RootKeywordCandidateResponse;
import com.mentorHub.api.dto.response.RootKeywordResponse;
import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordApprovalFacade {

    private final MenteeService menteeService;

    private final RootKeywordService rootKeywordService;

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

    /**
     * 키워드 승인/반려 요청을 처리하고, 관련 멘티들의 정보를 업데이트합니다.
     *
     * @param request 키워드 승인/반려 정보를 담은 요청 객체
     * @return 처리된 RootKeywordAliasEntity
     */
    public RootKeywordAliasEntity pubKeywordApproval(RootKeywordPutRequest request) {
        RootKeywordEntity rootKeyword = null;

        // '승인' 요청 처리: 키워드를 활성화하고 멘티의 키워드를 업데이트합니다.
        if (request.isActive()) {
            rootKeyword = rootKeywordService.findOrCreate(request.getRootKeywordId(), request.getAliasName());
            menteeService.updateKeywordsAndRefreshVector(request.getAliasName(), rootKeyword);
        }
        // '반려' 요청 처리: 멘티의 키워드에서 해당 내용을 null로 변경합니다.
        else if (request.isDeleted()) {
            rootKeywordService.deleteRootKeyword(request.getRootKeywordId());
            menteeService.updateKeywordsAndRefreshVector(request.getAliasName(), null);
        }

        // 최종적으로 alias의 상태를 업데이트하고 결과를 반환합니다.
        return rootKeywordService.pubKeywordApproval(request.toEntity(rootKeyword));
    }

    private float score(String alias, String canonical) {
        alias = alias.toLowerCase(Locale.ROOT);
        canonical = canonical.toLowerCase(Locale.ROOT);

        if (!canonical.contains(alias)) {
            return 0.0f;
        }

        return (float) alias.length() / canonical.length();
    }
}
