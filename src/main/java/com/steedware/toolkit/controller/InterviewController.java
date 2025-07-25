package com.steedware.toolkit.controller;

import com.steedware.toolkit.dto.InterviewRequest;
import com.steedware.toolkit.dto.InterviewResponse;
import com.steedware.toolkit.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview")
@Tag(name = "Interview Simulator", description = "AI-powered interview answer evaluation and feedback")
public class InterviewController {

    private final InterviewService interviewService;

    @Autowired
    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/answer-check")
    @Operation(summary = "Evaluate interview answer", description = "Analyze candidate's answer and provide detailed feedback with improvement suggestions")
    public ResponseEntity<InterviewResponse> evaluateAnswer(@Valid @RequestBody InterviewRequest request) {
        InterviewResponse response = interviewService.evaluateAnswer(request);
        return ResponseEntity.ok(response);
    }
}
