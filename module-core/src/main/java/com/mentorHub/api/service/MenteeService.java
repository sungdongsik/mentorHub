package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.query.MenteeQuery;
import lombok.RequiredArgsConstructor;
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

        menteeRepository.delete(en);

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
}
