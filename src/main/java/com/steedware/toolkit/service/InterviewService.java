package com.steedware.toolkit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.dto.InterviewRequest;
import com.steedware.toolkit.dto.InterviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class InterviewService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public InterviewService(AiService aiService) {
        this.aiService = aiService;
        this.objectMapper = new ObjectMapper();
    }

    public InterviewResponse evaluateAnswer(InterviewRequest request) {
        String prompt = buildInterviewEvaluationPrompt(request);
        String aiResponse = aiService.callOpenAi(prompt);
        return parseInterviewResponse(aiResponse, request.getQuestion());
    }

    private String buildInterviewEvaluationPrompt(InterviewRequest request) {
        return String.format("""
            Evaluate this interview answer for a %s %s position. Provide structured feedback in JSON format:
            {
              "scoreOutOf10": <1-10 rating>,
              "strengths": ["strength 1", "strength 2"],
              "improvementAreas": ["area 1", "area 2"],
              "detailedFeedback": "comprehensive feedback text",
              "idealAnswer": "example of strong answer",
              "followUpQuestions": ["follow-up 1", "follow-up 2"]
            }
            
            Evaluate based on:
            - Technical accuracy
            - Communication clarity
            - Problem-solving approach
            - Industry best practices knowledge
            - Experience demonstration
            - Confidence and professionalism
            
            Question: %s
            
            Candidate's Answer: %s
            
            Provide constructive feedback that helps the candidate improve.
            """, request.getLevel(), request.getRole(), request.getQuestion(), request.getCandidateAnswer());
    }

    private InterviewResponse parseInterviewResponse(String aiResponse, String question) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});

            return new InterviewResponse(
                question,
                (Integer) responseMap.getOrDefault("scoreOutOf10", 5),
                (List<String>) responseMap.getOrDefault("strengths", Arrays.asList()),
                (List<String>) responseMap.getOrDefault("improvementAreas", Arrays.asList()),
                (String) responseMap.get("detailedFeedback"),
                (String) responseMap.get("idealAnswer"),
                (List<String>) responseMap.getOrDefault("followUpQuestions", Arrays.asList())
            );
        } catch (Exception e) {
            return new InterviewResponse(
                question,
                5,
                Arrays.asList("Response provided"),
                Arrays.asList("Answer evaluation failed"),
                "Failed to evaluate answer: " + e.getMessage(),
                "Please provide a clear, structured answer with examples.",
                Arrays.asList("Can you elaborate on your approach?")
            );
        }
    }
}
