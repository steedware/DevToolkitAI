package com.steedware.toolkit.dto;

import java.util.List;

public class TestGenerationResponse {
    private String className;
    private List<String> testCases;
    private String generatedTestCode;
    private List<String> suggestions;

    public TestGenerationResponse() {}

    public TestGenerationResponse(String className, List<String> testCases, String generatedTestCode, List<String> suggestions) {
        this.className = className;
        this.testCases = testCases;
        this.generatedTestCode = generatedTestCode;
        this.suggestions = suggestions;
    }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public List<String> getTestCases() { return testCases; }
    public void setTestCases(List<String> testCases) { this.testCases = testCases; }
    public String getGeneratedTestCode() { return generatedTestCode; }
    public void setGeneratedTestCode(String generatedTestCode) { this.generatedTestCode = generatedTestCode; }
    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
}
