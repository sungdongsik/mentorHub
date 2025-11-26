package com.mentorHub.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.CreateMenteeRequest;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.util.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.*;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MenteeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("멘티 리스트 목록 키워드 없이 조회하기")
    void testGetMenteesNoKeyword() throws Exception {

        String url = "http://localhost:" + port + "/api/mentees";

        ResponseEntity<ApiResponse<PageResponse<MenteeResponse>>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<PageResponse<MenteeResponse>>>() {}
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData().getContents().get(0).getName()).isEqualTo("Mentee1");

        response.getBody().getData().getContents()
                .forEach(m -> System.out.println(m.getName()));
    }

    @Test
    @DisplayName("멘티 리스트 목록 키워드 포함 조회하기")
    void testGetMenteesYesKeyword() throws Exception {

        String url = "http://localhost:" + port + "/api/mentees?name=Mentee1&status=MENTEE&keyword=Java";

        ResponseEntity<ApiResponse<PageResponse<MenteeResponse>>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<PageResponse<MenteeResponse>>>() {}
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData().getContents().get(0).getName()).isEqualTo("Mentee10");

        response.getBody().getData().getContents()
                .forEach(m -> System.out.println(m.getName()));
    }

    @Test
    @DisplayName("멘티 글 등록")
    void testSetMentors() throws Exception {
        // 요청 객체 생성
        CreateMenteeRequest request = new CreateMenteeRequest();
        request.setTitle("Test Title");
        request.setContent("Test Content");
        request.setStartDate(LocalDateTime.now());
        request.setKeyword(new String[]{"Java", "jsp"});
        request.setJob("Test Job");

        String url = "http://localhost:" + port + "/api/mentees";

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
}