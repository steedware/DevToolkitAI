package com.steedware.toolkit.controller;

import com.steedware.toolkit.dto.TestGenerationRequest;
import com.steedware.toolkit.dto.TestGenerationResponse;
import com.steedware.toolkit.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@Tag(name = "Test Generation", description = "AI-powered test case and JUnit test generation")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate unit tests", description = "Generate comprehensive JUnit5 test cases for given Java class")
    public ResponseEntity<TestGenerationResponse> generateTests(@Valid @RequestBody TestGenerationRequest request) {
        TestGenerationResponse response = testService.generateTests(request);
        return ResponseEntity.ok(response);
    }
}
