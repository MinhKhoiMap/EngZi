package com.engzi.Utils;

import java.io.Serializable;

public class Question implements Serializable {
    private String english_word;
    private String userAnswer;

    public Question(String english_word) {
        this.english_word = english_word;
    }

    public String getEnglish_word() {
        return english_word;
    }

    public void setEnglish_word(String english_word) {
        this.english_word = english_word;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
