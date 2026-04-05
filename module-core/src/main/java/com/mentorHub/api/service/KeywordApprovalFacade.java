package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.RootKeywordPutRequest;
import com.mentorHub.api.dto.response.RootKeywordCandidateResponse;
import com.mentorHub.api.dto.response.RootKeywordResponse;
import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordApprovalFacade {

    private final MenteeService menteeService;
    private final RootKeywordService rootKeywordService;

    public List<RootKeywordResponse> getKeywordApproval(RootKeywordAliasEntity request, int page, int size) {
        List<RootKeywordAliasEntity> aliases = rootKeywordService.getKeywordApproval(request, page, size);

        if (aliases.isEmpty()) {
            return List.of();
        }

        // ROOT 테이블에서 ACTIVE 값 가져오기
        List<RootKeywordEntity> allRoots = rootKeywordService.getKeywordActive();

        return aliases.stream()
                .map(alias -> RootKeywordResponse.from(alias, findCandidates(alias, allRoots)))
                .toList();
    }

    /**
     * 특정 별칭과 유사한 루트 키워드 후보군을 계산하여 거리순으로 정렬합니다.
     */
    private List<RootKeywordCandidateResponse> findCandidates(RootKeywordAliasEntity alias, List<RootKeywordEntity> allRoots) {
        String targetName = alias.getAliasName().toLowerCase();

        return allRoots.stream()
                .sorted(Comparator.comparingInt(root ->
                        StringUtils.getLevenshteinDistance(targetName, root.getCanonicalName().toLowerCase())))
                .map(root -> RootKeywordCandidateResponse.builder()
                        .rootKeywordId(root.getRootKeywordId())
                        .canonicalName(root.getCanonicalName())
                        .build())
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

        if (request.isActive()) {
            rootKeyword = rootKeywordService.findOrCreate(request.getRootKeywordId(), request.getAliasName());
            menteeService.updateKeywordsAndRefreshVector(request.getAliasName(), rootKeyword);
        } else if (request.isDeleted()) {
            rootKeywordService.deleteRootKeyword(request.getRootKeywordId());
            menteeService.updateKeywordsAndRefreshVector(request.getAliasName(), null);
        }

        return rootKeywordService.pubKeywordApproval(request.toEntity(rootKeyword));
    }
}
