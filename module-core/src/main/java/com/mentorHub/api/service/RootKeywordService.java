package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.KeywordCreateRequest;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import com.mentorHub.api.repository.RootKeywordAliasRepository;
import com.mentorHub.api.repository.RootKeywordRepository;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RootKeywordService {

    private final RootKeywordRepository rootKeywordRepository;

    private final RootKeywordAliasRepository rootKeywordAliasRepository;

    public List<MenteeKeywordEntity> findByMenteeKeyword(List<KeywordCreateRequest> request, MenteeEntity en) {
        List<String> aliasNames = request.stream()
                .map(KeywordCreateRequest::getKeyword)
                .distinct()
                .toList();

        Map<String, RootKeywordEntity> rootKeywordMap = findRootKeywordsAsMap(aliasNames);

        return aliasNames.stream()
                .map(keyword -> createMenteeKeyword(en, keyword, rootKeywordMap))
                .toList();
    }

    private MenteeKeywordEntity createMenteeKeyword(MenteeEntity mentee, String keyword, Map<String, RootKeywordEntity> rootKeywordMap) {
        RootKeywordEntity root = rootKeywordMap.get(keyword.toLowerCase(Locale.ROOT));
        return (root != null) ? MenteeKeywordEntity.of(mentee, root) : MenteeKeywordEntity.of(mentee, keyword);
    }

    private Map<String, RootKeywordEntity> findRootKeywordsAsMap(List<String> aliasNames) {
        List<RootKeywordEntity> rootKeywords = rootKeywordRepository.findCanonicalName(aliasNames);
        return rootKeywords.stream()
                .collect(Collectors.toMap(
                        e -> e.getCanonicalName().toLowerCase(Locale.ROOT),
                        Function.identity()
                ));
    }

    public void ensureKeywordsExist(List<KeywordCreateRequest> keywords) {

        // 1. 요청된 키워드 이름 목록 추출 (중복 제거)
        List<String> keywordNames = keywords.stream()
                .map(KeywordCreateRequest::getKeyword)
                .distinct()
                .toList();

        Set<String> existingNames = rootKeywordAliasRepository.findAllByAliasNameIn(keywordNames)
                .stream()
                .map(RootKeywordAliasEntity::getAliasName)
                .collect(Collectors.toSet());


        List<RootKeywordAliasEntity> newAliases = keywordNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(RootKeywordAliasEntity::create)
                .toList();


        rootKeywordAliasRepository.saveAll(newAliases);
    }

    public List<RootKeywordAliasEntity> getKeywordApproval(RootKeywordEntity request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rootKeywordAliasRepository.findByStatus(request.getStatus(), pageable);
    }

    public RootKeywordAliasEntity pubKeywordApproval(RootKeywordAliasEntity request) {
        return rootKeywordAliasRepository.save(request);
    }

    public List<RootKeywordEntity> getKeywordActive() {
        return rootKeywordRepository.findAllByStatus(RootKeywordAliasStatus.ACTIVE);
    }

    public RootKeywordEntity getRootKeyword(Long rootKeywordId) {
        return rootKeywordRepository.findById(rootKeywordId).orElse(null);
    }

    public RootKeywordEntity createRootKeyword(String canonicalName) {
        return rootKeywordRepository.save(RootKeywordEntity.builder()
                .canonicalName(canonicalName)
                .status(RootKeywordAliasStatus.ACTIVE)
                .build());
    }

    /**
     * 요청에 따라 RootKeyword를 찾거나, 존재하지 않으면 새로 생성합니다.
     *
     * @param rootKeywordId 조회할 RootKeyword의 ID (null일 수 있음)
     * @param aliasName     새로 생성할 경우 사용될 이름
     * @return 찾아내거나 새로 생성된 RootKeywordEntity
     */
    public RootKeywordEntity findOrCreate(Long rootKeywordId, String aliasName) {
        if (rootKeywordId != null) {
            return getRootKeyword(rootKeywordId);
        }
        return createRootKeyword(aliasName);
    }

    public void deleteRootKeyword(Long rootKeywordId) {
        rootKeywordRepository.deleteById(rootKeywordId);
    }
}
