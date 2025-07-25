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

        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API: " + e.getMessage(), e);
        }
    }
}
