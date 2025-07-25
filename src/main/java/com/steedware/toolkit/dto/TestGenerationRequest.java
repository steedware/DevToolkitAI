package com.steedware.toolkit.dto;

import jakarta.validation.constraints.NotBlank;

public class TestGenerationRequest {
    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Class name cannot be empty")
    private String className;

    private String testFramework = "junit5";

    public TestGenerationRequest() {}

    public TestGenerationRequest(String code, String className, String testFramework) {
        this.code = code;
        this.className = className;
        this.testFramework = testFramework;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getTestFramework() { return testFramework; }
    public void setTestFramework(String testFramework) { this.testFramework = testFramework; }
}
