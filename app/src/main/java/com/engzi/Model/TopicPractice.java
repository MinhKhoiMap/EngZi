package com.engzi.Model;

import java.io.Serializable;

public class TopicPractice implements Serializable {
    int id;
    String img,title,description;
    float completion_level;
    boolean isCompleted;

    public TopicPractice(String img, String title, String description, boolean isCompleted) {
        this.img = img;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
