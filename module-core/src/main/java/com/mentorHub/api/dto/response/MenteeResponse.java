package com.mentorHub.api.dto.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 25.11.21
 * 멘티 리스트 조회 Response Dto
 * 주요 포함 정보:
 * - menteeId: 멘티 고유 ID
 * - name: 멘티 이름
 * - status: 멘토링 상태
 * - keyword: 관심 키워드 목록
 * - title: 멘티 요청 제목
 * - content: 멘티 요청 내용
 * - job: 직업
 * - reviews: 리뷰 목록
 */

@Getter
public class MenteeResponse{
    private Long menteeId; // 멘티 고유 ID

    private String name;

    private String status;

    private String[] keyword;

    private String title;

    private String content;

    private String job;

    private List<ReviewsResponse> reviews = new ArrayList<>();

}
