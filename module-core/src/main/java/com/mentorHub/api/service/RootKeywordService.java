package com.mentorHub.api.service;

import com.mentorHub.api.entity.KeywordAliasEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import com.mentorHub.api.repository.KeywordAliasRepository;
import com.mentorHub.api.repository.RootKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RootKeywordService {

    private final RootKeywordRepository rootKeywordRepository;

    private final KeywordAliasRepository keywordAliasRepository;

    public RootKeywordEntity findByCanonicalName(String keyword){
        return rootKeywordRepository.findByCanonicalName(keyword).orElseThrow();
    }

    public List<KeywordAliasEntity> findAllByAliasNameIn(List<String> request) {
        return keywordAliasRepository.findAllByAliasNameIn(request);
    }

    public RootKeywordEntity setRootKeyword(RootKeywordEntity en) {
        return rootKeywordRepository.save(en);
    }

    public void setKeywordAlias(KeywordAliasEntity en) {
        keywordAliasRepository.save(en);
    }

}
