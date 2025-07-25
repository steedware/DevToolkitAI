package com.steedware.toolkit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.dto.BugExplainerRequest;
import com.steedware.toolkit.dto.BugExplainerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BugService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BugService(AiService aiService) {
        this.aiService = aiService;
        this.objectMapper = new ObjectMapper();
    }

    public BugExplainerResponse explainBug(BugExplainerRequest request) {
        String prompt = buildBugExplanationPrompt(request);
        String aiResponse = aiService.callOpenAi(prompt);
        return parseBugExplanationResponse(aiResponse);
    }

    private String buildBugExplanationPrompt(BugExplainerRequest request) {
        return String.format("""
            Analyze the following %s stack trace and provide detailed explanation in JSON format:
            {
              "errorType": "type of error",
              "explanation": "human-readable explanation",
              "rootCause": "likely root cause",
              "possibleSolutions": ["solution 1", "solution 2", "solution 3"],
              "codeExample": "corrected code example if applicable",
              "severityLevel": <1-5 severity rating>
            }
            
            Provide:
            - Clear explanation of what went wrong
            - Why this error occurred
            - Step-by-step solutions
            - Prevention strategies
            - Code examples for fixes
            
            Stack trace:
            %s
            
            %s
            """,
            request.getLanguage(),
            request.getStackTrace(),
            request.getCodeContext() != null ? "Code context:\n" + request.getCodeContext() : "");
    }

    private BugExplainerResponse parseBugExplanationResponse(String aiResponse) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});

            return new BugExplainerResponse(
                (String) responseMap.get("errorType"),
                (String) responseMap.get("explanation"),
                (String) responseMap.get("rootCause"),
                (List<String>) responseMap.getOrDefault("possibleSolutions", Arrays.asList()),
                (String) responseMap.get("codeExample"),
                (Integer) responseMap.getOrDefault("severityLevel", 3)
            );
        } catch (Exception e) {
            return new BugExplainerResponse(
                "Unknown Error",
                "Failed to analyze the stack trace: " + e.getMessage(),
                "Analysis failed",
                Arrays.asList("Please check the stack trace format and try again"),
                null,
                3
            );
        }
    }
}
