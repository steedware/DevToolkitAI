package com.steedware.toolkit.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.config.AiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final AiConfig.OpenAiProperties openAiProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public AiService(RestTemplate restTemplate, AiConfig.OpenAiProperties openAiProperties) {
        this.restTemplate = restTemplate;
        this.openAiProperties = openAiProperties;
        this.objectMapper = new ObjectMapper();
    }

    public String callOpenAi(String prompt) {
        try {
            // Sprawdzenie czy klucz API jest skonfigurowany
            if (openAiProperties.getApiKey() == null ||
                openAiProperties.getApiKey().equals("your-api-key-here") ||
                openAiProperties.getApiKey().trim().isEmpty()) {
                return createErrorResponse("OpenAI API key is not configured. Please set OPENAI_API_KEY environment variable.");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openAiProperties.getApiKey());
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", openAiProperties.getModel());
            requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
            requestBody.put("max_tokens", openAiProperties.getMaxTokens());
            requestBody.put("temperature", openAiProperties.getTemperature());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                openAiProperties.getApiUrl(),
                HttpMethod.POST,
                entity,
                String.class
            );

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            return jsonResponse.path("choices").get(0).path("message").path("content").asText();

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                return createErrorResponse("Invalid OpenAI API key. Please check your OPENAI_API_KEY environment variable.");
            } else if (e.getStatusCode().value() == 429) {
                return createErrorResponse("OpenAI API rate limit exceeded. Please try again later.");
            } else {
                return createErrorResponse("OpenAI API error: " + e.getMessage());
            }
        } catch (Exception e) {
            return createErrorResponse("Failed to connect to OpenAI API. Please check your internet connection and API configuration.");
        }
    }

    private String createErrorResponse(String errorMessage) {
        return String.format("""
            {
              "qualityRating": 0,
              "badPractices": ["API Configuration Error"],
              "refactoringSuggestions": ["Configure OpenAI API key"],
              "cleanCodeIssues": ["Service unavailable"],
              "overallFeedback": "%s"
            }
            """, errorMessage);
    }
}
