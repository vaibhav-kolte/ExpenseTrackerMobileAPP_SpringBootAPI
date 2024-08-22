package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class MyExpenses {
    private int id;
    private String username;
    private String expenseName;
    private double expenseAmount;
    private String date;
    private String expenseType;

    public MyExpenses(int id, String username, String expenseName, double expenseAmount, String date, String expenseType) {
        this.id = id;
        this.username = username;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.date = date;
        this.expenseType = expenseType;
    }

    public MyExpenses(String username, String expenseName, double expenseAmount, String date, String expenseType) {
        this.username = username;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.date = date;
        this.expenseType = expenseType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    @NonNull
    @Override
    public String toString() {
//        return "id=" + id +
        return "username='" + username + '\'' +
                "\nexpenseName='" + expenseName + '\'' +
                "\nexpenseAmount=" + expenseAmount +
                "\ndate='" + formatDate(date) + '\'' +
                "\nexpenseType='" + expenseType + '\'';
    }

    private String formatDate(String date){
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return offsetDateTime.format(formatter);
    }
}
