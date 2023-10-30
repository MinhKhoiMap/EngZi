package com.engzi.Model;

import java.io.Serializable;
import java.util.List;

public class LessonPractice implements Serializable {
    String lessonID;
    String thumbnail;
    String topic_name;
    String lesson_name;
    List<String> list_cards;
    float completion_percent = 0;

    public LessonPractice() {
    }

    public LessonPractice(String lessonID, String thumbnail, String topic_name, String lesson_name, List<String> list_cards, float completion_percent) {
        this.lessonID = lessonID;
        this.thumbnail = thumbnail;
        this.topic_name = topic_name;
        this.lesson_name = lesson_name;
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

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
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

    @Override
    public String toString() {
        return "LessonPractice{" +
                "lessonID='" + lessonID + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", topic_name='" + topic_name + '\'' +
                ", description='" + lesson_name + '\'' +
                ", list_cards=" + list_cards +
                ", completion_percent=" + completion_percent +
                '}';
    }
}
