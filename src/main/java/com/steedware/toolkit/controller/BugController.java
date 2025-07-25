package com.steedware.toolkit.controller;

import com.steedware.toolkit.dto.BugExplainerRequest;
import com.steedware.toolkit.dto.BugExplainerResponse;
import com.steedware.toolkit.service.BugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bug")
@Tag(name = "Bug Explainer", description = "AI-powered stack trace analysis and bug explanation")
public class BugController {

    private final BugService bugService;

    @Autowired
    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @PostMapping("/explain")
    @Operation(summary = "Explain bug from stack trace", description = "Analyze stack trace and provide detailed explanation with possible solutions")
    public ResponseEntity<BugExplainerResponse> explainBug(@Valid @RequestBody BugExplainerRequest request) {
        BugExplainerResponse response = bugService.explainBug(request);
        return ResponseEntity.ok(response);
    }
}
