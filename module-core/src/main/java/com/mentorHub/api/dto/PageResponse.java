package com.mentorHub.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 25.11.21
 * paging Response Dto
 * 주요 포함 정보:
 * - contents: 데이터
 * - totalCount: 전체 데이터 개수
 * - totalPages: 전체 페이지 개수
 * - pageSize: 한 페이지에 보여줄 데이터 개수
 */

@Getter
@Builder
public class PageResponse<T> {
    private List<T> contents;

    private int totalCount;

    private int totalPages;

    private int pageSize;

}
