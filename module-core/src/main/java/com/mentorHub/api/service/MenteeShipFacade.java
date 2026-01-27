package com.mentorHub.api.service;

import com.mentorHub.api.dto.request.KeywordCreateRequest;
import com.mentorHub.api.dto.request.ReviewCreateRequest;
import com.mentorHub.api.dto.request.ReviewPutRequest;
import com.mentorHub.api.entity.*;
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

    private final RootKeywordService rootKeywordService;

    @Transactional(readOnly = true)
    public List<MenteeEntity> getMenteesWithReview(MenteeEntity request) {

        // 멘티 조회
        List<MenteeEntity> mentees = menteeService.getMentees(request);

        // 멘티 글 PK(writingId) 추출
        List<Long> writingIds = mentees.stream()
                .map(MenteeEntity::getWritingId)
                .collect(Collectors.toList());

        // Keyword 엔티티들을 한꺼번에 조회
        List<MenteeKeywordEntity> keywords = menteeService.findAllByWritingIdIn(writingIds);

        // 멘티, 키워드 매핑
        mapKeyword(mentees, keywords);

        // 조회 결과가 없으면 불필요한 쿼리 방지
        if (mentees.isEmpty()) {
            return mentees;
        }

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


    private void mapKeyword(List<MenteeEntity> mentees, List<MenteeKeywordEntity> keywords) {
        // Mentee의 ID를 Key로, Keyword 문자열 리스트를 Value로 하는 Map 생성
        Map<Long, List<MenteeKeywordEntity>> keywordMap =
                keywords.stream()
                        .collect(Collectors.groupingBy(
                                r -> r.getMentee().getWritingId()
                        ));

        // 특정 멘티의 키워드 리스트 가져오기
        for (MenteeEntity mentee : mentees) {
            mentee.addKeywords(
                    keywordMap.getOrDefault(
                            mentee.getWritingId(),
                            Collections.emptyList()
                    )
            );
        }
    }

    @Transactional
    public MenteeEntity setMentees(MenteeEntity request, List<KeywordCreateRequest> keywords) {
        MenteeEntity en = menteeService.setMentees(request);

        // root_keyword save --> 이쪽부터 역할 분리하도록 로직 수정하기
        List<String> keywordNames = keywords.stream()
                .map(k -> k.getKeyword().trim().toLowerCase())
                .distinct()
                .toList();

        List<KeywordAliasEntity> aliases = rootKeywordService.findAllByAliasNameIn(keywordNames);

        Map<String, KeywordAliasEntity> aliasMap =
                aliases.stream()
                        .collect(Collectors.toMap(
                                a -> a.getAliasName(),
                                a -> a
                        ));

        List<String> notExists = keywordNames.stream()
                .filter(k -> !aliasMap.containsKey(k))
                .toList();

        // alias save
        for (String aliasName : notExists) {
            RootKeywordEntity root = rootKeywordService.setRootKeyword(RootKeywordEntity.create(aliasName));
            rootKeywordService.setKeywordAlias(KeywordAliasEntity.create(aliasName, root));
        }

        List<MenteeKeywordEntity> menteeKeyword = findByMenteeKeyword(keywords, en);
        menteeService.setMenteeKeyword(menteeKeyword);

        return en;
    }

    public List<MenteeKeywordEntity> findByMenteeKeyword(List<KeywordCreateRequest> request, MenteeEntity en) {

        return request.stream()
                .map(req ->
                        MenteeKeywordEntity.builder()
                                .keyword(req.getKeyword())
                                .rootKeyword(rootKeywordService
                                        .findByCanonicalName(req.getKeyword())
                                )
                                .mentee(en)
                                .build()
                )
                .toList();
    }


}
