package com.steedware.toolkit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.dto.CodeReviewRequest;
import com.steedware.toolkit.dto.CodeReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReviewService(AiService aiService) {
        this.aiService = aiService;
        this.objectMapper = new ObjectMapper();
    }

    public CodeReviewResponse reviewCode(CodeReviewRequest request) {
        String prompt = buildCodeReviewPrompt(request);
        String aiResponse = aiService.callOpenAi(prompt);
        return parseCodeReviewResponse(aiResponse, request.getFileName());
    }

    private String buildCodeReviewPrompt(CodeReviewRequest request) {
        return String.format("""
            Review this %s code and provide structured feedback in JSON format with the following structure:
            {
              "qualityRating": <1-10 rating>,
              "badPractices": ["practice1", "practice2"],
              "refactoringSuggestions": ["suggestion1", "suggestion2"],
              "cleanCodeIssues": ["issue1", "issue2"],
              "overallFeedback": "detailed feedback text"
            }
            
            Focus on:
            - Code quality and best practices
            - SOLID principles adherence
            - Clean code principles
            - Performance considerations
            - Security vulnerabilities
            - Maintainability issues
            
            Code to review:
            %s
            """, request.getLanguage(), request.getCode());
    }

    private CodeReviewResponse parseCodeReviewResponse(String aiResponse, String fileName) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});

            return new CodeReviewResponse(
                fileName,
                (Integer) responseMap.get("qualityRating"),
                (List<String>) responseMap.getOrDefault("badPractices", Arrays.asList()),
                (List<String>) responseMap.getOrDefault("refactoringSuggestions", Arrays.asList()),
                (List<String>) responseMap.getOrDefault("cleanCodeIssues", Arrays.asList()),
                (String) responseMap.get("overallFeedback")
            );
        } catch (Exception e) {
            return new CodeReviewResponse(
                fileName,
                5,
                Arrays.asList("Unable to parse AI response"),
                Arrays.asList("Please try again with valid code"),
                Arrays.asList("Response parsing failed"),
                "AI analysis failed: " + e.getMessage()
            );
        }
    }
}
