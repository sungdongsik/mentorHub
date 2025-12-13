package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.ReviewRepository;
import com.mentorHub.api.repository.query.MenteeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenteeShipService {

    private final ReviewRepository reviewRepository;

    private final MenteeRepository menteeRepository;

    private final MenteeQuery menteeQuery;

    private final MenteeApplicationRepository menteeApplicationRepository;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMentees(MenteeEntity request) {
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

    @Transactional(readOnly = true)
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    public ReviewEntity setReviews(ReviewEntity request) {
        // 필요 없던거였음.. 이미 컨트롤러에서 멘티 조회해서 넣어주고 있어서..
        //request.getMentee().getReviews().add(request);
        return reviewRepository.save(request);
    }

    public ReviewEntity deleteReviews(ReviewEntity request) {
        ReviewEntity en = findById(request);

        reviewRepository.delete(en);

        return en;
    }

    public ReviewEntity putReviews(ReviewEntity request) {
        return reviewRepository.save(request);
    }

    private ReviewEntity findById(ReviewEntity request) {
        return reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다!"));
    }

    public List<MenteeEntity> getMenteesWithReviews(List<MenteeEntity> mentees, List<ReviewEntity> reviews) {
        Map<Long, List<ReviewEntity>> reviewMap = reviews.stream()
                .collect(Collectors.groupingBy(r -> r.getMentee().getWritingId()));

        mentees.forEach(m ->
                m.setReviews(reviewMap.getOrDefault(m.getWritingId(), new ArrayList<>()))
        );

        return mentees;
    }
}
