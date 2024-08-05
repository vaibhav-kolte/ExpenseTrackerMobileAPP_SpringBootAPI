package com.mycode.myExpenseTracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class LoginAccount {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String myPassword;

    public LoginAccount() {
    }

    @Override
    public String toString() {
        return "LoginAccount{" +
                "username='" + username + '\'' +
                ", myPassword='" + myPassword + '\'' +
                '}';
    }
}
