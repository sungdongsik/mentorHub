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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RootKeywordService {

    private final RootKeywordRepository rootKeywordRepository;

    private final RootKeywordAliasRepository rootKeywordAliasRepository;

    public List<MenteeKeywordEntity> findByMenteeKeyword(List<KeywordCreateRequest> request, MenteeEntity en) {

        List<String> aliasName = request.stream()
                .map(KeywordCreateRequest::getKeyword)
                .distinct()
                .toList();

        // 상태와 상관없이 이름으로 모든 Alias 조회 (PENDING 포함)
        List<RootKeywordEntity> rootKeywordAlias = rootKeywordRepository.findCanonicalName(aliasName);

        Map<String, RootKeywordEntity> rootMap = rootKeywordAlias.stream()
                        .collect(Collectors.toMap(
                                e -> e.getCanonicalName().toLowerCase(Locale.ROOT),
                                Function.identity()
                        ));

        return aliasName.stream()
                .map(keyword -> {
                    RootKeywordEntity root = rootMap.get(keyword);

                    // root 있으면 root 기준
                    if (root != null) {
                        return MenteeKeywordEntity.of(en, root);
                    }

                    // 없으면 사용자 입력 그대로
                    return MenteeKeywordEntity.of(en, keyword);
                })
                .toList();
    }

    public void ensureKeywordsExist(List<KeywordCreateRequest> keywords) {

        // 1. 요청된 키워드 이름 목록 추출 (중복 제거)
        List<String> keywordNames = keywords.stream()
                .map(KeywordCreateRequest::getKeyword)
                .distinct()
                .toList();

        // alias 키워드들을 조건 걸어서 조회
        List<RootKeywordAliasEntity> aliases = rootKeywordAliasRepository.findAllByAliasNameIn(keywordNames);

        // 이미 존재하는 alias 이름만 Set 으로 추출
        Set<String> existingNames = aliases.stream()
                .map(RootKeywordAliasEntity::getAliasName)
                .collect(Collectors.toSet());

        List<RootKeywordAliasEntity> newAliases = keywordNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(RootKeywordAliasEntity::create) // create 메서드가 PENDING 상태로 생성
                .toList();

        rootKeywordAliasRepository.saveAll(newAliases);
    }

    public List<RootKeywordAliasEntity> getKeywordApproval(RootKeywordEntity request) {
        return rootKeywordAliasRepository.findByStatus(request.getStatus());
    }

    public RootKeywordEntity pubKeywordApproval(RootKeywordEntity request) {
        return rootKeywordRepository.save(request);
    }

    public List<RootKeywordEntity> getKeywordActive() {
        return rootKeywordRepository.findAllByStatus(RootKeywordAliasStatus.ACTIVE);
    }
}
