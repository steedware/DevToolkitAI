package com.steedware.toolkit.dto;

import java.util.List;

public class CommitMessageResponse {
    private String primaryMessage;
    private List<String> alternativeMessages;
    private String conventionType;
    private String explanation;

    public CommitMessageResponse() {}

    public CommitMessageResponse(String primaryMessage, List<String> alternativeMessages, String conventionType, String explanation) {
        this.primaryMessage = primaryMessage;
        this.alternativeMessages = alternativeMessages;
        this.conventionType = conventionType;
        this.explanation = explanation;
    }

    public String getPrimaryMessage() { return primaryMessage; }
    public void setPrimaryMessage(String primaryMessage) { this.primaryMessage = primaryMessage; }
    public List<String> getAlternativeMessages() { return alternativeMessages; }
    public void setAlternativeMessages(List<String> alternativeMessages) { this.alternativeMessages = alternativeMessages; }
    public String getConventionType() { return conventionType; }
    public void setConventionType(String conventionType) { this.conventionType = conventionType; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
