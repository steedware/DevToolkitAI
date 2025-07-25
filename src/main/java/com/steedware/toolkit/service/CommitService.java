package com.steedware.toolkit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steedware.toolkit.dto.CommitMessageRequest;
import com.steedware.toolkit.dto.CommitMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CommitService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommitService(AiService aiService) {
        this.aiService = aiService;
        this.objectMapper = new ObjectMapper();
    }

    public CommitMessageResponse generateCommitMessage(CommitMessageRequest request) {
        String prompt = buildCommitMessagePrompt(request);
        String aiResponse = aiService.callOpenAi(prompt);
        return parseCommitMessageResponse(aiResponse, request.getConventionType());
    }

    private String buildCommitMessagePrompt(CommitMessageRequest request) {
        return String.format("""
            Analyze the following git diff and generate commit messages using %s commits convention.
            Provide response in JSON format:
            {
              "primaryMessage": "main commit message",
              "alternativeMessages": ["alternative 1", "alternative 2", "alternative 3"],
              "explanation": "explanation of changes"
            }
            
            Rules for conventional commits:
            - feat: new feature
            - fix: bug fix
            - docs: documentation changes
            - style: formatting, missing semicolons, etc
            - refactor: code change that neither fixes bug nor adds feature
            - test: adding missing tests
            - chore: changes to build process or auxiliary tools
            
            Keep messages concise (max 50 chars for subject), imperative mood.
            Include scope in parentheses if applicable: feat(auth): add login validation
            
            Git diff:
            %s
            """, request.getConventionType(), request.getGitDiff());
    }

    private CommitMessageResponse parseCommitMessageResponse(String aiResponse, String conventionType) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});

            return new CommitMessageResponse(
                (String) responseMap.get("primaryMessage"),
                (List<String>) responseMap.getOrDefault("alternativeMessages", Arrays.asList()),
                conventionType,
                (String) responseMap.get("explanation")
            );
        } catch (Exception e) {
            return new CommitMessageResponse(
                "chore: update code",
                Arrays.asList("fix: resolve issues", "refactor: improve code structure"),
                conventionType,
                "Failed to analyze git diff: " + e.getMessage()
            );
        }
    }
}
