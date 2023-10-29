package com.engzi.Model;

import java.io.Serializable;

public class User implements Serializable {
    String UID;
    String profileName;
    String email;
    String createdDate;

    public User() {
    }

    public User(String UID, String profileName, String email, String createdDate) {
        this.UID = UID;
        this.profileName = profileName;
        this.email = email;
        this.createdDate = createdDate;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
