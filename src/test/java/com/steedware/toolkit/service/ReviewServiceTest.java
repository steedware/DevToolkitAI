package com.steedware.toolkit.service;

import com.steedware.toolkit.dto.CodeReviewRequest;
import com.steedware.toolkit.dto.CodeReviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private AiService aiService;

    @InjectMocks
    private ReviewService reviewService;

    private CodeReviewRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new CodeReviewRequest();
        validRequest.setCode("public class Test { private String name; }");
        validRequest.setFileName("Test.java");
        validRequest.setLanguage("java");
    }

    @Test
    void shouldReviewCodeSuccessfully() {
        String mockAiResponse = """
            {
              "qualityRating": 7,
              "badPractices": ["Missing getter/setter methods"],
              "refactoringSuggestions": ["Add proper encapsulation"],
              "cleanCodeIssues": ["Class lacks documentation"],
              "overallFeedback": "Good basic structure but needs improvement"
            }
            """;

        when(aiService.callOpenAi(anyString())).thenReturn(mockAiResponse);

        CodeReviewResponse response = reviewService.reviewCode(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getFileName()).isEqualTo("Test.java");
        assertThat(response.getQualityRating()).isEqualTo(7);
        assertThat(response.getBadPractices()).contains("Missing getter/setter methods");
        assertThat(response.getOverallFeedback()).contains("Good basic structure");
    }

    @Test
    void shouldHandleInvalidAiResponse() {
        when(aiService.callOpenAi(anyString())).thenReturn("invalid json response");

        CodeReviewResponse response = reviewService.reviewCode(validRequest);

        assertThat(response).isNotNull();
        assertThat(response.getQualityRating()).isEqualTo(5);
        assertThat(response.getBadPractices()).contains("Unable to parse AI response");
    }
}
