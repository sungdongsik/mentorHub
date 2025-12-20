package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.ReviewCreateRequest;
import com.mentorHub.api.dto.request.ReviewPutRequest;
import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenteeShipFacade {

    private final MenteeService menteeService;

    private final ReviewService reviewService;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMenteesWithReview(MenteeEntity request) {

        // 멘티 조회
        List<MenteeEntity> mentees = menteeService.getMentees(request);

        // 조회 결과가 없으면 불필요한 쿼리 방지
        if (mentees.isEmpty()) {
            return mentees;
        }

        // 멘티 글 PK(writingId) 추출
        List<Long> writingIds = mentees.stream()
                .map(MenteeEntity::getWritingId)
                .collect(Collectors.toList());

        // writingId IN 쿼리로 리뷰 한 번에 조회
        List<ReviewEntity> reviews = reviewService.findByWritingIds(writingIds);

        // 멘티, 리뷰 매핑
        mapReviews(mentees, reviews);

        return mentees;
    }

    private void mapReviews(List<MenteeEntity> mentees, List<ReviewEntity> reviews) {
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

    @Transactional
    public ReviewEntity setReviews(ReviewCreateRequest request) {
        MenteeEntity mentee = menteeService.findById(request.getWritingId());
        ReviewEntity en = reviewService.setReviews(request.toEntity(mentee));

        return en;
    }

    @Transactional
    public ReviewEntity putReviews(ReviewPutRequest request) {
        MenteeEntity mentee = menteeService.findById(request.getWritingId());
        ReviewEntity en = reviewService.putReviews(request.toEntity(mentee));

        return en;
    }
}
