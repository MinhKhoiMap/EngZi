package com.engzi.Model;

public class LessionPractice {
    String lessionID;
    String imageURL;
    String lessionName;
    String description;
    float completion_percent;

    public LessionPractice() {
    }

    public LessionPractice(String lessionID, String imageURL, String lessionName, String description, float completion_percent) {
        this.lessionID = lessionID;
        this.imageURL = imageURL;
        this.lessionName = lessionName;
        this.description = description;
        this.completion_percent = completion_percent;
    }

    public String getLessionID() {
        return lessionID;
    }

    public void setLessionID(String lessionID) {
        this.lessionID = lessionID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLessionName() {
        return lessionName;
    }

    public void setLessionName(String lessionName) {
        this.lessionName = lessionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCompletion_percent() {
        return completion_percent;
    }

    public void setCompletion_percent(float completion_percent) {
        this.completion_percent = completion_percent;
    }
}
