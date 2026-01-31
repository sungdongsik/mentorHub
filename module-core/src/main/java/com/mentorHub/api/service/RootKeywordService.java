package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.KeywordCreateRequest;
import com.mentorHub.api.entity.RootKeywordAliasEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.MenteeKeywordEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import com.mentorHub.api.repository.RootKeywordAliasRepository;
import com.mentorHub.api.repository.RootKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RootKeywordService {

    private final RootKeywordRepository rootKeywordRepository;

    private final RootKeywordAliasRepository rootKeywordAliasRepository;

    public List<RootKeywordAliasEntity> findAllByAliasNameIn(List<String> request) {
        return rootKeywordAliasRepository.findAllByAliasNameIn(request);
    }

    public RootKeywordEntity setRootKeyword(RootKeywordEntity en) {
        return rootKeywordRepository.save(en);
    }

    public void setKeywordAlias(RootKeywordAliasEntity en) {
        rootKeywordAliasRepository.save(en);
    }

    public List<MenteeKeywordEntity> findByMenteeKeyword(List<KeywordCreateRequest> request, MenteeEntity en) {
        return request.stream()
                .map(req ->
                        MenteeKeywordEntity.builder()
                                .keyword(req.getKeyword())
                                .rootKeyword(rootKeywordRepository.findByCanonicalName(req.getKeyword()).orElseThrow())
                                .mentee(en)
                                .build()
                )
                .toList();
    }

    public void ensureKeywordsExist(List<KeywordCreateRequest> keywords) {

        // 중복 키워드 제거
        List<String> keywordNames = keywords.stream()
                .map(k -> k.getKeyword())
                .distinct()
                .toList();

        // alias 키워드들을 조건 걸어서 조회
        List<RootKeywordAliasEntity> aliases = findAllByAliasNameIn(keywordNames);

        // 이미 존재하는 alias 이름만 Set 으로 추출
        Set<String> existingNames = aliases.stream()
                .map(RootKeywordAliasEntity::getAliasName)
                .collect(Collectors.toSet());

        // 없는 키워드들만 골라서 생성
        keywordNames.stream()
                .filter(canonicalName -> !existingNames.contains(canonicalName))
                .forEach(canonicalName -> {
                    // root_keyword 생성
                    RootKeywordEntity root = setRootKeyword(RootKeywordEntity.create(canonicalName));
                    // alias_keyword 생성
                    setKeywordAlias(RootKeywordAliasEntity.create(canonicalName, root));
                });
    }

    public List<RootKeywordEntity> getKeywordApproval(RootKeywordEntity request) {
        return rootKeywordRepository.findAllByStatus(request.getStatus());
    }
}
