package com.mentorHub.api.service;

import com.mentorHub.api.entity.MenteeApplicationEntity;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import com.mentorHub.api.repository.MenteeApplicationRepository;
import com.mentorHub.api.repository.MenteeRepository;
import com.mentorHub.api.repository.ReviewRepository;
import com.mentorHub.api.repository.query.MenteeQuery;
import com.mentorHub.api.repository.query.ReviewQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

    private final ReviewQuery reviewQuery;

    private final MenteeApplicationRepository menteeApplicationRepository;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMentees(MenteeEntity request) {
        // 멘티 목록 조회
        List<MenteeEntity> mentees = menteeQuery.getMentees(request);

        // 조회 결과가 없으면 불필요한 쿼리 방지
        if (mentees.isEmpty()) {
            return mentees;
        }

        // 멘티 글 PK(writingId) 추출
        List<Long> writingIds = mentees.stream()
                .map(MenteeEntity::getWritingId)
                .collect(Collectors.toList());

        // writingId IN 쿼리로 리뷰 한 번에 조회
        List<ReviewEntity> reviews = reviewQuery.getReviews(writingIds);

        // 멘티 ↔ 리뷰 매핑
        getMenteesWithReviews(mentees, reviews);

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

    public void getMenteesWithReviews(List<MenteeEntity> mentees, List<ReviewEntity> reviews) {
        // writingId 기준으로 리뷰 그룹핑
        Map<Long, List<ReviewEntity>> reviewMap =
                reviews.stream()
                        .collect(Collectors.groupingBy(
                                r -> r.getMentee().getWritingId()
                        ));

        // 각 멘티에 해당 리뷰들 추가
        for (MenteeEntity mentee : mentees) {
            mentee.addReviews(
                    reviewMap.getOrDefault(
                            mentee.getWritingId(),
                            Collections.emptyList()
                    )
            );
        }
    }
}
