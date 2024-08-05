package com.mycode.myExpenseTracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private double transactionAmount;

    @Column(nullable = false)
    private String transactionType;

    public Transaction() {
    }

    public Transaction(String username, String date, double transactionAmount, String transactionType) {
        this.username = username;
        this.date = date;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
