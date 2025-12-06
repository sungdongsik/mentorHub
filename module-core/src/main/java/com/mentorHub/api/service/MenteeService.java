package com.mentorHub.api.service;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.*;
import com.mentorHub.api.dto.response.MenteeApplicationResponse;
import com.mentorHub.api.dto.response.MenteeCommandResponse;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.dto.response.ReviewsResponse;
import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.ReviewRepository;
import com.mentorHub.api.repository.query.MenteeQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MenteeService {

    private final MenteeRepository menteeRepository;

    private final MenteeQuery menteeQuery;

    private final MenteeApplicationRepository menteeApplicationRepository;

    private final ReviewService reviewService;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMentees(MenteeEntity request) {
        List<MenteeEntity> mentees = menteeQuery.getMentees(request);
        
        List<ReviewEntity> reviews = reviewService.getReviews();

        Map<Long, List<ReviewEntity>> reviewMap = reviews.stream()
                .collect(Collectors.groupingBy(r -> r.getMentee().getWritingId()));

        mentees.forEach(m ->
                m.setReviews(reviewMap.getOrDefault(m.getWritingId(), new ArrayList<>()))
        );

        return mentees;
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
