package com.mentorHub.api.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 25.11.21
 * 리뷰 리스트 조회 Response Dto
 * 주요 포함 정보:
 * - reviewsId: 리뷰 고유 ID
 * - menteeId: 리뷰를 작성한 멘티 ID
 * - content: 리뷰 내용
 * - name: 작성자 이름
 * - insertDate: 리뷰 등록일
 */

@Getter
public class ReviewsResponse {
    private Long reviewsId;

    private Long menteeId;

    private String content;

    private String name;

    private LocalDateTime insertDate;
}
