package com.steedware.toolkit.controller;

import com.steedware.toolkit.dto.CommitMessageRequest;
import com.steedware.toolkit.dto.CommitMessageResponse;
import com.steedware.toolkit.service.CommitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commit")
@Tag(name = "Commit Message Generator", description = "AI-powered git commit message generation following conventions")
public class CommitController {

    private final CommitService commitService;

    @Autowired
    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @PostMapping("/message")
    @Operation(summary = "Generate commit message", description = "Generate conventional commit messages based on git diff")
    public ResponseEntity<CommitMessageResponse> generateCommitMessage(@Valid @RequestBody CommitMessageRequest request) {
        CommitMessageResponse response = commitService.generateCommitMessage(request);
        return ResponseEntity.ok(response);
    }
}
