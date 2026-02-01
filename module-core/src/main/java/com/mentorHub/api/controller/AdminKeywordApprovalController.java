package com.mentorHub.api.controller;

import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.RootKeywordPutRequest;
import com.mentorHub.api.dto.request.RootKeywordRequest;
import com.mentorHub.api.dto.response.RootKeywordPutResponse;
import com.mentorHub.api.dto.response.RootKeywordResponse;
import com.mentorHub.api.entity.RootKeywordEntity;
import com.mentorHub.api.service.RootKeywordService;
import com.mentorHub.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/keyword-approval")
@RequiredArgsConstructor
public class AdminKeywordApprovalController {

    private final RootKeywordService rootKeywordService;

    @GetMapping
    public ApiResponse<PageResponse<RootKeywordResponse>> getKeywordApproval(@ModelAttribute RootKeywordRequest request) {
        log.info("request: {}", request);
        List<RootKeywordEntity> approval = rootKeywordService.getKeywordApproval(request.toEntity());

        List<RootKeywordResponse> responses = approval.stream()
                .map(RootKeywordResponse::from)
                .toList();

        return ApiResponse.success(PageResponse.of(responses));
    }

    @PutMapping
    public ApiResponse<RootKeywordPutResponse> pubKeywordApproval(@RequestBody RootKeywordPutRequest request) {
        log.info("request: {}", request);
        RootKeywordEntity en = rootKeywordService.pubKeywordApproval(request.toEntity());

        return ApiResponse.success(RootKeywordPutResponse.from(en));
    }


}
