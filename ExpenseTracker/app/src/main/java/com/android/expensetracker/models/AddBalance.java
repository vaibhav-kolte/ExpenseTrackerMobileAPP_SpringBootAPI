package com.android.expensetracker.models;

import java.util.Date;

public class AddBalance {
    private String username;
    private String date;
    private double transactionAmount;
    private String transactionType;

    public AddBalance(String username, String date, double transactionAmount, String transactionType) {
        this.username = username;
        this.date = date;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "AddBalance{" +
                "username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
