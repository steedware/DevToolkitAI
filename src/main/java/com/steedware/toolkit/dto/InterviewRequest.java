package com.steedware.toolkit.dto;

import jakarta.validation.constraints.NotBlank;

public class InterviewRequest {
    @NotBlank(message = "Question cannot be empty")
    private String question;

    @NotBlank(message = "Answer cannot be empty")
    private String candidateAnswer;

    private String role = "Java Developer";
    private String level = "Mid";

    public InterviewRequest() {}

    public InterviewRequest(String question, String candidateAnswer, String role, String level) {
        this.question = question;
        this.candidateAnswer = candidateAnswer;
        this.role = role;
        this.level = level;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getCandidateAnswer() { return candidateAnswer; }
    public void setCandidateAnswer(String candidateAnswer) { this.candidateAnswer = candidateAnswer; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}
