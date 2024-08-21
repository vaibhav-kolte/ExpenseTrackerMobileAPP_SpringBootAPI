package com.myproject.expensetacker.model;


import androidx.annotation.NonNull;


public class Account {

    private final String username;
    private final String myPassword;

    public Account(String username, String myPassword) {
        this.username = username;
        this.myPassword = myPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getMyPassword() {
        return myPassword;
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" + "username='" + username + '\'' +
                ", myPassword='" + myPassword + '\'' +
                '}';
    }
}
