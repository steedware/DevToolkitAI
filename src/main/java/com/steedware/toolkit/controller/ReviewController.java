package com.steedware.toolkit.controller;

import com.steedware.toolkit.dto.CodeReviewRequest;
import com.steedware.toolkit.dto.CodeReviewResponse;
import com.steedware.toolkit.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@Tag(name = "Code Review", description = "AI-powered code review and quality analysis")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(summary = "Review code quality", description = "Analyze code for best practices, clean code principles, and provide improvement suggestions")
    public ResponseEntity<CodeReviewResponse> reviewCode(@Valid @RequestBody CodeReviewRequest request) {
        CodeReviewResponse response = reviewService.reviewCode(request);
        return ResponseEntity.ok(response);
    }
}
