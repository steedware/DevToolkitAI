package com.steedware.toolkit.dto;

import java.util.List;

public class CodeReviewResponse {
    private String fileName;
    private Integer qualityRating;
    private List<String> badPractices;
    private List<String> refactoringSuggestions;
    private List<String> cleanCodeIssues;
    private String overallFeedback;

    public CodeReviewResponse() {}

    public CodeReviewResponse(String fileName, Integer qualityRating, List<String> badPractices,
                             List<String> refactoringSuggestions, List<String> cleanCodeIssues, String overallFeedback) {
        this.fileName = fileName;
        this.qualityRating = qualityRating;
        this.badPractices = badPractices;
        this.refactoringSuggestions = refactoringSuggestions;
        this.cleanCodeIssues = cleanCodeIssues;
        this.overallFeedback = overallFeedback;
    }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public Integer getQualityRating() { return qualityRating; }
    public void setQualityRating(Integer qualityRating) { this.qualityRating = qualityRating; }
    public List<String> getBadPractices() { return badPractices; }
    public void setBadPractices(List<String> badPractices) { this.badPractices = badPractices; }
    public List<String> getRefactoringSuggestions() { return refactoringSuggestions; }
    public void setRefactoringSuggestions(List<String> refactoringSuggestions) { this.refactoringSuggestions = refactoringSuggestions; }
    public List<String> getCleanCodeIssues() { return cleanCodeIssues; }
    public void setCleanCodeIssues(List<String> cleanCodeIssues) { this.cleanCodeIssues = cleanCodeIssues; }
    public String getOverallFeedback() { return overallFeedback; }
    public void setOverallFeedback(String overallFeedback) { this.overallFeedback = overallFeedback; }
}
