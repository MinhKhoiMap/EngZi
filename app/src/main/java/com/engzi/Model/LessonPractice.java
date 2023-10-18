package com.engzi.Model;

import java.io.Serializable;

public class LessonPractice {
    private int id;
    private String img;
    private String title;
    private String description;
    private float completion_level;

    public LessonPractice() {
    }

    public LessonPractice(String img, String title, String description) {
        this.img = img;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
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

    public float getCompletion_level() {
        return completion_level;
    }
}
