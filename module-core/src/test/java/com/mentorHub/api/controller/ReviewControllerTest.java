package com.mentorHub.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.ReviewCreateRequest;
import com.mentorHub.api.dto.request.ReviewPutRequest;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.api.dto.response.ReviewResponse;
import com.mentorHub.common.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("리뷰 조회")
    public void getReviews() {
        String url = "http://localhost:" + port + "/api/reviews";

        ResponseEntity<ApiResponse<List<ReviewResponse>>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<List<ReviewResponse>>>() {}
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData().get(0).getContent()).isEqualTo("예시 내용입니다.");

        response.getBody().getData()
                .forEach(m -> System.out.println(m.getContent()));
    }

    @Test
    @DisplayName("리뷰 저장")
    public void setReviews() throws Exception {

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .writingId(1L)
                .title("test1")
                .content("content1")
                .rating(4.5)
                .name("홍기돌~")
                .build();

        String url = "http://localhost:" + port + "/api/reviews";

        // POST 요청
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, request, ApiResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());

        System.out.println(
                new ObjectMapper().writerWithDefaultPrettyPrinter()
                        .writeValueAsString(response.getBody())
        );
    }


    @Test
    @DisplayName("리뷰 수정")
    public void putReviews() throws Exception {

        ReviewPutRequest request = ReviewPutRequest.builder()
                .reviewId(5L)
                .writingId(1L)
                .title("test1")
                .content("content1")
                .rating(4.5)
                .build();

        String url = "http://localhost:" + port + "/api/reviews";

        // POST 요청
        restTemplate.put(url, request);
    }
}