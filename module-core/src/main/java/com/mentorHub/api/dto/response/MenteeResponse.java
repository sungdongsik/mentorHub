package com.mentorHub.api.dto.response;

import com.mentorHub.api.entity.MenteeEntity;
import com.util.ApplicationStausType;
import com.util.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 25.11.21
 * 멘티 리스트 조회 Response Dto
 * 주요 포함 정보:
 * - writingId: 멘티 글 고유 ID
 * - name: 멘티 이름
 * - status: 멘토링 상태
 * - keyword: 관심 키워드 목록
 * - title: 멘티 요청 제목
 * - content: 멘티 요청 내용
 * - job: 직업
 * - reviews: 리뷰 목록
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenteeResponse {
    private Long writingId;

    private String name;

    private UserType status;

    private LocalDateTime startDate;

    private String[] keyword;

    private String title;

    private String content;

    private String job;

    private List<ReviewsResponse> reviews;

}
