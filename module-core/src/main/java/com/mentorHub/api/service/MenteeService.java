package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.KeywordCreateRequest;
import com.mentorHub.api.dto.response.MenteeSummaryResponse;
import com.mentorHub.api.entity.*;
import com.mentorHub.api.repository.*;
import com.mentorHub.api.repository.query.MenteeQuery;
import com.mentorHub.api.vector.VectorService;
import com.mentorHub.common.BusinessException;
import com.util.RootKeywordAliasStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenteeService {

    private final MenteeRepository menteeRepository;
    private final MenteeQuery menteeQuery;
    private final MenteeApplicationRepository menteeApplicationRepository;
    private final MenteeKeywordRepository menteeKeywordRepository;
    private final VectorService vectorService;
    private final MenteeVectorAssemblerService menteeVectorAssemblerService;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMentees(MenteeEntity request) {
        // 멘티 목록 조회
        return menteeQuery.getMentees(request);
    }

    public MenteeEntity setMentees(MenteeEntity request){
        return menteeRepository.save(request);
    }

    public MenteeEntity deleteMentees(MenteeEntity request) {

        MenteeEntity en = findById(request.getWritingId());

        int deletedMentees = menteeRepository.deleteMentee(en.getWritingId());

        if (deletedMentees == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "이미 삭제되었거나 존재하지 않는 멘티 글입니다.");
        }

        return en;
    }

    public MenteeEntity putMentees(MenteeEntity request, List<KeywordCreateRequest> keywords) {
        MenteeEntity en = menteeRepository.save(request);
        //List<MenteeKeywordEntity> menteeKeyword = findByMenteeKeyword(keywords, en);
        //en.replaceKeywords(menteeKeyword);

        return en;
    }

    public MenteeApplicationEntity createMenteesApplication(MenteeApplicationEntity request) {
        return menteeApplicationRepository.save(request);
    }

    public MenteeApplicationEntity updateApplicationStatus(MenteeApplicationEntity request) {
        return menteeApplicationRepository.save(request);
    }

    public MenteeEntity findById(Long writingId) {
        return menteeRepository.findById(writingId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다!"));
    }

    public List<MenteeKeywordEntity> findAllByWritingIdIn(List<Long> writingIds) {
        return menteeKeywordRepository.findAllByMentee_WritingIdIn(writingIds);
    }

    public List<MenteeEntity> findAllByKeywordApproval(Long rootKeywordId) {
        return menteeRepository.findDistinctByKeywords_RootKeyword_RootKeywordId(rootKeywordId);
    }

    public List<MenteeSummaryResponse> findByWritingIdsAndActiveKeywords(List<Long> writingIds) {

        List<MenteeEntity> mentees = menteeRepository.findByWritingIdsAndActiveKeywords(writingIds, RootKeywordAliasStatus.ACTIVE);

        List<MenteeSummaryResponse> summaries = mentees.stream()
                .map(MenteeSummaryResponse::from)
                .toList();

        return summaries;
    }

    public List<MenteeEntity> updateMenteeKeywords(String keyword, RootKeywordEntity rootKeyword) {

        List<MenteeKeywordEntity> menteeKeywords = menteeKeywordRepository.findAllByKeyword(keyword);

        menteeKeywords.forEach(mk -> mk.setRootKeyword(rootKeyword));

        return menteeKeywords.stream()
                .map(MenteeKeywordEntity::getMentee)
                .toList();
    }

    /**
     * 멘티의 키워드를 업데이트하고, 변경된 멘티 정보에 대한 벡터 인덱스를 갱신합니다.
     *
     * @param aliasName   업데이트할 키워드의 별칭
     * @param rootKeyword 연결할 RootKeyword (null일 경우 연결 해제)
     */
    public void updateKeywordsAndRefreshVector(String aliasName, RootKeywordEntity rootKeyword) {
        List<MenteeEntity> affectedMentees = updateMenteeKeywords(aliasName, rootKeyword);

        if (!affectedMentees.isEmpty()) {
            List<Document> docs = affectedMentees.stream()
                    .map(menteeVectorAssemblerService::assemble)
                    .toList();
            vectorService.saveAll(docs);
        }
    }
}
