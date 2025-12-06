package com.mentorHub.api.dto.request;

import com.mentorHub.api.entity.MenteeEntity;
import com.mentorHub.api.entity.ReviewEntity;
import lombok.Getter;
import lombok.ToString;


/**
 * 25.12.06
 * 리뷰 등록 Request Dto
 * 주요 포함 정보:
 * - writingId: 멘티 글 ID
 * - title: 제목
 * - content: 내용
 * - rating: 평점
 */


@Getter
@ToString
public class ReviewCreateRequest {
    private Long writingId;

    private String title;

    private String content;

    private double rating;

    private String name;

    public ReviewEntity toEntity(MenteeEntity en) {
        return ReviewEntity.builder()
                .title(title)
                .content(content)
                .mentee(en)
                .rating(rating)
                .name(name)
                .build();
    }
}
