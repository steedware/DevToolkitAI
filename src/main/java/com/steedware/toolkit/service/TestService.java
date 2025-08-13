package com.steedware.toolkit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.dto.TestGenerationRequest;
import com.steedware.toolkit.dto.TestGenerationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TestService(AiService aiService) {
        this.aiService = aiService;
        this.objectMapper = new ObjectMapper();
    }

    public TestGenerationResponse generateTests(TestGenerationRequest request) {
        String prompt = buildTestGenerationPrompt(request);
        String aiResponse = aiService.callOpenAi(prompt);

        // Check if AI response contains error information
        if (aiResponse.contains("API Configuration Error") ||
            aiResponse.contains("OpenAI API") ||
            aiResponse.contains("rate limit") ||
            aiResponse.contains("Invalid OpenAI")) {

            return new TestGenerationResponse(
                request.getClassName(),
                Arrays.asList("Usługa AI nie jest dostępna"),
                "// " + extractErrorMessage(aiResponse),
                Arrays.asList("Skonfiguruj klucz OpenAI API lub spróbuj ponownie za chwilę")
            );
        }

        return parseTestGenerationResponse(aiResponse, request.getClassName());
    }

    private String buildTestGenerationPrompt(TestGenerationRequest request) {
        return String.format("""
            Generate comprehensive %s tests for the following Java class. 
            Provide response in JSON format:
            {
              "testCases": ["test case description 1", "test case description 2"],
              "generatedTestCode": "complete test class code",
              "suggestions": ["testing suggestion 1", "suggestion 2"]
            }
            
            Class name: %s
            
            Generate tests for:
            - Happy path scenarios
            - Edge cases
            - Error conditions
            - Boundary values
            - Null/empty inputs
            - Integration scenarios if applicable
            
            Use %s framework with proper annotations and assertions.
            
            Code to test:
            %s
            """, request.getTestFramework(), request.getClassName(),
                 request.getTestFramework(), request.getCode());
    }

    private TestGenerationResponse parseTestGenerationResponse(String aiResponse, String className) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});

            return new TestGenerationResponse(
                className,
                (List<String>) responseMap.getOrDefault("testCases", Arrays.asList()),
                (String) responseMap.get("generatedTestCode"),
                (List<String>) responseMap.getOrDefault("suggestions", Arrays.asList())
            );
        } catch (Exception e) {
            return new TestGenerationResponse(
                className,
                Arrays.asList("Failed to generate test cases"),
                "// Test generation failed: " + e.getMessage(),
                Arrays.asList("Please try again with valid code")
            );
        }
    }

    private String extractErrorMessage(String aiResponse) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});
            return (String) responseMap.getOrDefault("overallFeedback", "Problem z usługą AI");
        } catch (Exception e) {
            return "Problem z usługą AI - spróbuj ponownie za chwilę";
        }
    }
}
