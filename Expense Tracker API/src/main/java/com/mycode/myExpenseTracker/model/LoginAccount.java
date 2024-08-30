package com.mycode.myExpenseTracker.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "LoginAccount")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginAccount {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String myPassword;
    @Column
    private String name;
    @Column
    private String type;
    @Lob
    @Column(name = "imagedata",length = 1000)
    private byte[] imageData;

    @Override
    public String toString() {
        return "LoginAccount{" +
                "username='" + username + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
