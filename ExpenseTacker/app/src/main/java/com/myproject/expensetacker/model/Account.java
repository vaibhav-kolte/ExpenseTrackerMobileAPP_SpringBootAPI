package com.myproject.expensetacker.model;


import androidx.annotation.NonNull;


public class Account {

    private final String username;
    private final String myPassword;
    private final String imageUrl;

    public Account(String username, String myPassword, String imageUrl) {
        this.username = username;
        this.myPassword = myPassword;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
