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

    private static final int DEFAULT_PAGE = 1;

    private static final int PAGE_SIZE = 20;

    // 리스트만 넣으면 내부에서 기본 page, pageSize로 처리
    public static <T> PageResponse<T> of(List<T> list) {
        int page = DEFAULT_PAGE;
        int pageSize = PAGE_SIZE;

        int totalCount = list.size();
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);

        int fromIndex = Math.min((page - 1) * pageSize, totalCount);
        int toIndex = Math.min(fromIndex + pageSize, totalCount);
        List<T> pageList = list.subList(fromIndex, toIndex);

        return PageResponse.<T>builder()
                .contents(pageList)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .pageSize(pageSize)
                .build();
    }
}

