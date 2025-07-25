package com.steedware.toolkit.dto;

import java.util.List;

public class BugExplainerResponse {
    private String errorType;
    private String explanation;
    private String rootCause;
    private List<String> possibleSolutions;
    private String codeExample;
    private Integer severityLevel;

    public BugExplainerResponse() {}

    public BugExplainerResponse(String errorType, String explanation, String rootCause,
                               List<String> possibleSolutions, String codeExample, Integer severityLevel) {
        this.errorType = errorType;
        this.explanation = explanation;
        this.rootCause = rootCause;
        this.possibleSolutions = possibleSolutions;
        this.codeExample = codeExample;
        this.severityLevel = severityLevel;
    }

    public String getErrorType() { return errorType; }
    public void setErrorType(String errorType) { this.errorType = errorType; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) { this.rootCause = rootCause; }
    public List<String> getPossibleSolutions() { return possibleSolutions; }
    public void setPossibleSolutions(List<String> possibleSolutions) { this.possibleSolutions = possibleSolutions; }
    public String getCodeExample() { return codeExample; }
    public void setCodeExample(String codeExample) { this.codeExample = codeExample; }
    public Integer getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(Integer severityLevel) { this.severityLevel = severityLevel; }
}
