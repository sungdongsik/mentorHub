package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.query.MenteeQuery;
import com.mentorHub.common.BusinessException;
import lombok.RequiredArgsConstructor;
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

    public MenteeEntity putMentees(MenteeEntity request) {
        return menteeRepository.save(request);
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

    // 로직 고민해볼 필요가 있을 거 같음.. 전체 조회라서 필터를 걸어서 보낼지 전체 다 보낼지 고민좀 해보고 결정하기..
    public List<MenteeEntity> findAll() {
        return menteeRepository.findAll();
    }
}
