package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

public class Transaction {
    private long id;
    private String username;
    private String date;
    private double transactionAmount;
    private String transactionType;

    public Transaction(long id, String username, String date, double transactionAmount, String transactionType) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @NonNull
    @Override
    public String toString() {
        return "username='" + username + '\'' +
                "\ndate='" + date + '\'' +
                "\ntransactionAmount=" + transactionAmount +
                "\ntransactionType='" + transactionType + '\'';
    }
}
