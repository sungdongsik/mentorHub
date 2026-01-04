package com.mentorHub.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentorHub.api.dto.PageResponse;
import com.mentorHub.api.dto.request.MenteeCreateRequest;
import com.mentorHub.api.dto.request.MenteeDeleteRequest;
import com.mentorHub.api.dto.response.MenteeResponse;
import com.mentorHub.common.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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

        System.out.println("list: " + response.getBody().getData().getContents());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData().getContents().get(0).getName()).isEqualTo("홍길동");

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
        assertThat(response.getBody().getData().getContents().get(0).getName()).isEqualTo("tse");

        response.getBody().getData().getContents()
                .forEach(m -> System.out.println(m.getName()));
    }

    @Test
    @DisplayName("멘티 글 등록")
    void testSetMentors() throws Exception {
        // 요청 객체 생성
        MenteeCreateRequest request = new MenteeCreateRequest();
        request.setTitle("Test Title");
        request.setContent("Test Content");
        request.setStartDate(LocalDateTime.now());
        request.setKeyword("Java,jsp");
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

    @Test
    @DisplayName("멘티 글 삭제하기")
    public void deleteMentee() {
        String url = "http://localhost:" + port + "/api/mentees";
        MenteeDeleteRequest request = new MenteeDeleteRequest();
        request.setWritingId(1L);
        request.setUserId(1L);

        HttpEntity<MenteeDeleteRequest> entity = new HttpEntity<>(request);

        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
    }


    @Test
    @DisplayName("멘티 글 ID 보내지 않고 삭제하기")
    public void deleteMentee2() {
        // 멘티 삭제 API 호출 URL
        String url = "http://localhost:" + port + "/api/mentees";

        // 멘티 글 ID(writingId)를 일부러 세팅하지 않은 요청 객체
        MenteeDeleteRequest request = new MenteeDeleteRequest();
        request.setUserId(1L);

        // DELETE 요청에 body를 담기 위한 HttpEntity
        HttpEntity<MenteeDeleteRequest> entity = new HttpEntity<>(request);

        // 멘티 삭제 API 호출
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                ApiResponse.class
        );

        // 필수값 누락으로 인해 400 BAD_REQUEST 반환되는지 검증
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());

        // Validation 메시지에 "멘티 글 ID는 필수입니다."가 포함되어 있는지 확인
        assertTrue(response.getBody().getData().toString()
                .contains("멘티 글 ID는 필수입니다."));
    }
}