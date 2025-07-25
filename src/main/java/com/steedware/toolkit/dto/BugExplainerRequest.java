package com.steedware.toolkit.dto;

import jakarta.validation.constraints.NotBlank;

public class BugExplainerRequest {
    @NotBlank(message = "Stack trace cannot be empty")
    private String stackTrace;

    private String codeContext;
    private String language = "java";

    public BugExplainerRequest() {}

    public BugExplainerRequest(String stackTrace, String codeContext, String language) {
        this.stackTrace = stackTrace;
        this.codeContext = codeContext;
        this.language = language;
    }

    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
    public String getCodeContext() { return codeContext; }
    public void setCodeContext(String codeContext) { this.codeContext = codeContext; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
