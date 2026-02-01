package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.RootKeywordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordApprovalFacade {

    private final MenteeService menteeService;

    private final RootKeywordService rootKeywordService;

    private final VectorService vectorService;

    public RootKeywordEntity pubKeywordApproval(RootKeywordEntity request) {

        // PENDING -> ACTIVE 업데이트 해주기
        RootKeywordEntity en = rootKeywordService.pubKeywordApproval(request);

        // 해당 루트 키워드를 사용 중인 멘티들을 조회
        List<MenteeEntity> mentees = menteeService.findAllByKeywordApproval(en);

        // 각 멘티의 정보를 벡터 DB에 다시 반영
        for (MenteeEntity mentee : mentees) {
            vectorService.saveMenteeDocument(mentee);
        }

        return en;
    }
}
