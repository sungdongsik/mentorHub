package com.mentorHub.api.service;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.mentorHub.api.repository.RootKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RootKeywordService {

    private final RootKeywordRepository rootKeywordRepository;

    public RootKeywordEntity findByCanonicalName(String keyword){
        return rootKeywordRepository.findByCanonicalName(keyword).orElseThrow();
    }
}
