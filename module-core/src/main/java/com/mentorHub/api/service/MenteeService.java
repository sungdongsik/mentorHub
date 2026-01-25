package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.KeywordCreateRequest;
import com.mentorHub.api.entity.*;
import com.mentorHub.api.repository.*;
import com.mentorHub.api.repository.query.MenteeQuery;
import com.mentorHub.common.BusinessException;
import com.util.MenteeRecruitmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    private final RootKeywordRepository rootKeywordRepository;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMentees(MenteeEntity request) {
        // 멘티 목록 조회
        return menteeQuery.getMentees(request);
    }

    public MenteeEntity setMentees(MenteeEntity request, List<KeywordCreateRequest> keywords){
        MenteeEntity en = menteeRepository.save(request);
        List<MenteeKeywordEntity> menteeKeyword = findByMenteeKeyword(keywords, en);
        setMenteeKeyword(menteeKeyword);

        return en;
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
        List<MenteeKeywordEntity> menteeKeyword = findByMenteeKeyword(keywords, en);
        en.replaceKeywords(menteeKeyword);

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
        return menteeKeywordRepository.findAllByMenteeWritingIdIn(writingIds);
    }

    public List<MenteeEntity> findByKeywords(List<String> keywords) {
        return menteeKeywordRepository.findByKeywords(keywords, MenteeRecruitmentStatus.RECRUITING, PageRequest.of(0, 10));
    }

    public List<MenteeKeywordEntity> findByMenteeKeyword(List<KeywordCreateRequest> request, MenteeEntity en) {

        return request.stream()
                .map(req ->
                        MenteeKeywordEntity.builder()
                            .keyword(req.getKeyword())
                            .rootKeyword(rootKeywordRepository
                                    .findByCanonicalName(req.getKeyword())
                                    .orElseThrow())
                            .mentee(en)
                            .build()
                )
                .toList();
    }

    public void setMenteeKeyword(List<MenteeKeywordEntity> keywords) {
        menteeKeywordRepository.saveAll(keywords);
    }
}
