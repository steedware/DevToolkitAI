package com.steedware.toolkit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.dto.CodeReviewRequest;
import com.steedware.toolkit.dto.CodeReviewResponse;
import com.steedware.toolkit.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReviewCodeSuccessfully() throws Exception {
        CodeReviewRequest request = new CodeReviewRequest("public class Test {}", "Test.java", "java");
        CodeReviewResponse response = new CodeReviewResponse(
            "Test.java", 8,
            Arrays.asList("Good structure"),
            Arrays.asList("Add documentation"),
            Arrays.asList("Consider naming conventions"),
            "Overall good code quality"
        );

        when(reviewService.reviewCode(any(CodeReviewRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("Test.java"))
                .andExpect(jsonPath("$.qualityRating").value(8))
                .andExpect(jsonPath("$.overallFeedback").value("Overall good code quality"));
    }

    @Test
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        CodeReviewRequest invalidRequest = new CodeReviewRequest("", "Test.java", "java");

        mockMvc.perform(post("/api/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}
