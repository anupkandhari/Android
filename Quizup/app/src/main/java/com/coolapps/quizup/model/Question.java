package com.coolapps.quizup.model;

public class Question {
    private String question;
    private Boolean isTrue;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }

    public Question(String question, Boolean isTrue) {
        this.question = question;
        this.isTrue = isTrue;
    }
}
