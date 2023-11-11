package com.engzi.Model;

import java.io.Serializable;
import java.util.List;

public class LessonPractice implements Serializable {
    String lessonID;
    String thumbnail;
    String topic_name;
    String lesson_name;
    List<String> list_cards;
    int last_position_card = -1;

    public LessonPractice() {
    }

    public LessonPractice(String lessonID, String thumbnail, String topic_name, String lesson_name,
                          List<String> list_cards, int last_position_card) {
        this.lessonID = lessonID;
        this.thumbnail = thumbnail;
        this.topic_name = topic_name;
        this.lesson_name = lesson_name;
        this.last_position_card = last_position_card;
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

    public int getLast_position_card() {
        return last_position_card;
    }

    public void setLast_position_card(int last_position_card) {
        this.last_position_card = last_position_card;
    }

    public float getCompletion_percent() {
        if (list_cards != null) {
            return last_position_card / (float) list_cards.size();
        }
        return 0;
    }
}
