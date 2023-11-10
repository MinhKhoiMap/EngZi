package com.engzi.Utils;

public class FillBlankQuestion extends Question {
    String question;

    public FillBlankQuestion(String english_word, String sentence) {
        super(english_word);
        this.question = sentence;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
