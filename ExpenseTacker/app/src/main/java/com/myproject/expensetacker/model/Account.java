package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

public class Account {

    private final String username;
    private final String myPassword;
    private final String name;
    private String type;
    private StringBuffer imageData;

    public Account(String username, String myPassword, String name, String type,
                   StringBuffer imageData) {
        this.username = username;
        this.myPassword = myPassword;
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public String getUsername() {
        return username;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public String getImageUrl() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StringBuffer getImageData() {
        return imageData;
    }

    public void setImageData(StringBuffer imageData) {
        this.imageData = imageData;
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", imageData=" + imageData +
                '}';
    }
}
