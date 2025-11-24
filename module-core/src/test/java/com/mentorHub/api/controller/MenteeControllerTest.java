package com.mentorHub.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;



@SpringBootTest
@AutoConfigureMockMvc
class MenteeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("멘티 리스트 목록")
    void testGetMentors() throws Exception {

        mockMvc.perform(get("/api/mentors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data.contents").isArray())
                .andExpect(jsonPath("$.data.totalCount").value(20))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(20))
                .andDo(print());
    }

    @Test
    @DisplayName("멘티 글 등록")
    void testSetMentors() throws Exception {

        mockMvc.perform(post("/api/mentors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andDo(print());
    }
}