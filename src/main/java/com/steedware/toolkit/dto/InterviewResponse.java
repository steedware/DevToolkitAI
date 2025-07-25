package com.steedware.toolkit.dto;

import java.util.List;

public class InterviewResponse {
    private String question;
    private Integer scoreOutOf10;
    private List<String> strengths;
    private List<String> improvementAreas;
    private String detailedFeedback;
    private String idealAnswer;
    private List<String> followUpQuestions;

    public InterviewResponse() {}

    public InterviewResponse(String question, Integer scoreOutOf10, List<String> strengths,
                           List<String> improvementAreas, String detailedFeedback,
                           String idealAnswer, List<String> followUpQuestions) {
        this.question = question;
        this.scoreOutOf10 = scoreOutOf10;
        this.strengths = strengths;
        this.improvementAreas = improvementAreas;
        this.detailedFeedback = detailedFeedback;
        this.idealAnswer = idealAnswer;
        this.followUpQuestions = followUpQuestions;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Integer getScoreOutOf10() { return scoreOutOf10; }
    public void setScoreOutOf10(Integer scoreOutOf10) { this.scoreOutOf10 = scoreOutOf10; }
    public List<String> getStrengths() { return strengths; }
    public void setStrengths(List<String> strengths) { this.strengths = strengths; }
    public List<String> getImprovementAreas() { return improvementAreas; }
    public void setImprovementAreas(List<String> improvementAreas) { this.improvementAreas = improvementAreas; }
    public String getDetailedFeedback() { return detailedFeedback; }
    public void setDetailedFeedback(String detailedFeedback) { this.detailedFeedback = detailedFeedback; }
    public String getIdealAnswer() { return idealAnswer; }
    public void setIdealAnswer(String idealAnswer) { this.idealAnswer = idealAnswer; }
    public List<String> getFollowUpQuestions() { return followUpQuestions; }
    public void setFollowUpQuestions(List<String> followUpQuestions) { this.followUpQuestions = followUpQuestions; }
}
