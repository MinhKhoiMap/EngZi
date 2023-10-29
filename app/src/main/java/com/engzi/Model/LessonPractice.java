package com.engzi.Model;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.List;

public class LessonPractice implements Serializable {
    String lessonID;
    String thumbnail;
    String topic_name;
    String description;
    List<String> list_cards;
    float completion_percent;

    public LessonPractice() {
    }

    public LessonPractice(String lessonID, String thumbnail, String topic_name, String description, List<String> list_cards, float completion_percent) {
        this.lessonID = lessonID;
        this.thumbnail = thumbnail;
        this.topic_name = topic_name;
        this.description = description;
        this.completion_percent = completion_percent;
        this.list_cards = list_cards;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getList_cards() {
        return list_cards;
    }

    public void setList_cards(List<String> list_cards) {
        this.list_cards = list_cards;
    }

    public float getCompletion_percent() {
        return completion_percent;
    }

    public void setCompletion_percent(float completion_percent) {
        this.completion_percent = completion_percent;
    }
}
