package com.steedware.toolkit.dto;

import jakarta.validation.constraints.NotBlank;

public class CodeReviewRequest {
    @NotBlank(message = "Code cannot be empty")
    private String code;

    private String fileName;
    private String language = "java";

    public CodeReviewRequest() {}

    public CodeReviewRequest(String code, String fileName, String language) {
        this.code = code;
        this.fileName = fileName;
        this.language = language;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
