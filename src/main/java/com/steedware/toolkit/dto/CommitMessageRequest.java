package com.steedware.toolkit.dto;

import jakarta.validation.constraints.NotBlank;

public class CommitMessageRequest {
    @NotBlank(message = "Git diff cannot be empty")
    private String gitDiff;

    private String conventionType = "conventional";

    public CommitMessageRequest() {}

    public CommitMessageRequest(String gitDiff, String conventionType) {
        this.gitDiff = gitDiff;
        this.conventionType = conventionType;
    }

    public String getGitDiff() { return gitDiff; }
    public void setGitDiff(String gitDiff) { this.gitDiff = gitDiff; }
    public String getConventionType() { return conventionType; }
    public void setConventionType(String conventionType) { this.conventionType = conventionType; }
}
