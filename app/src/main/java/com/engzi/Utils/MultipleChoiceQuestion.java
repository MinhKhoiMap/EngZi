package com.engzi.Utils;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    List<String> answerList;
    String cover_img_url;

    public MultipleChoiceQuestion(String english_word, List<String> answerList, String cover_img_url) {
        super(english_word);
        this.answerList = answerList;
        this.cover_img_url = cover_img_url;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }
}
