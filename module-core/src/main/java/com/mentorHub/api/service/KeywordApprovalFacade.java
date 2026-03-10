package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.RootKeywordPutRequest;
import com.mentorHub.api.dto.response.RootKeywordCandidateResponse;
import com.mentorHub.api.dto.response.RootKeywordResponse;
import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
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

    // 1차 필터링을 위한 Levenshtein 거리 임계값 (이 값 이하일 때만 후보로 선정)
    private static final int LEVENSHTEIN_THRESHOLD = 3;

    // 문자열 유사도 계산을 위한 유틸리티 인스턴스 (Thread-safe 하므로 static final로 재사용)
    private static final JaroWinklerSimilarity JARO_WINKLER_SIMILARITY = new JaroWinklerSimilarity();

    private static final LevenshteinDistance LEVENSHTEIN_DISTANCE = LevenshteinDistance.getDefaultInstance();

    public List<RootKeywordResponse> getKeywordApproval(RootKeywordAliasEntity request, int page, int size) {
        List<RootKeywordAliasEntity> aliases = rootKeywordService.getKeywordApproval(request, page, size);

        if (aliases.isEmpty()) {
            return List.of();
        }

        // ROOT 테이블에서 ACTIVE 값 가져오기
        List<RootKeywordEntity> allRoots = rootKeywordService.getKeywordActive();

        return aliases.stream()
                .map(alias -> {
                    String aliasLower = alias.getAliasName().toLowerCase(Locale.ROOT);

                    List<RootKeywordCandidateResponse> candidates = allRoots.stream()
                            // 1차 필터: Levenshtein Distance를 사용하여 편집 거리가 가까운 키워드만 필터링
                            .filter(root -> levenshteinFilter(aliasLower, root.getCanonicalName().toLowerCase(Locale.ROOT)))
                            .map(root -> RootKeywordCandidateResponse.builder()
                                    .rootKeywordId(root.getRootKeywordId())
                                    .canonicalName(root.getCanonicalName())
                                    // 최종 점수: Jaro-Winkler Similarity를 사용하여 유사도 점수 계산 (0.0 ~ 1.0)
                                    .score(calculateJaroWinklerScore(aliasLower, root.getCanonicalName().toLowerCase(Locale.ROOT)))
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

        if (request.isActive()) {
            rootKeyword = rootKeywordService.findOrCreate(request.getRootKeywordId(), request.getAliasName());
            menteeService.updateKeywordsAndRefreshVector(request.getAliasName(), rootKeyword);
        } else if (request.isDeleted()) {
            rootKeywordService.deleteRootKeyword(request.getRootKeywordId());
            menteeService.updateKeywordsAndRefreshVector(request.getAliasName(), null);
        }

        return rootKeywordService.pubKeywordApproval(request.toEntity(rootKeyword));
    }

    /**
     * Levenshtein Distance를 계산하여 임계값 이하인지 확인합니다.
     * 편집 거리(삽입, 삭제, 대체 횟수)가 작을수록 두 문자열이 유사합니다.
     */
    private boolean levenshteinFilter(String s1, String s2) {
        return LEVENSHTEIN_DISTANCE.apply(s1, s2) <= LEVENSHTEIN_THRESHOLD;
    }

    /**
     * Jaro-Winkler Similarity를 계산하여 반환합니다.
     * 공통 접두사에 가중치를 두어 오타나 변형에 대해 더 정확한 유사도를 제공합니다.
     * @return 0.0 (불일치) ~ 1.0 (완전 일치) 사이의 값
     */
    private float calculateJaroWinklerScore(String s1, String s2) {
        return JARO_WINKLER_SIMILARITY.apply(s1, s2).floatValue();
    }
}
