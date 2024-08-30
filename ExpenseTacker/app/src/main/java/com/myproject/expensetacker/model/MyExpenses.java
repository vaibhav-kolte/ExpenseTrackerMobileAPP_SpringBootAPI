package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

import com.myproject.expensetacker.utils.Utils;

import java.io.Serializable;

public class MyExpenses implements Serializable {
    private long id;
    private String username;
    private String expenseName;
    private double expenseAmount;
    private String date;
    private String expenseType;
    private String transactionType;

    public MyExpenses(long id, String username, String expenseName,
                      double expenseAmount, String date, String expenseType, String transactionType) {
        this.id = id;
        this.username = username;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.date = date;
        this.expenseType = expenseType;
        this.transactionType = transactionType;
    }

    public MyExpenses(String username, String expenseName, double expenseAmount,
                      String date, String expenseType, String transactionType) {
        this.username = username;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.date = date;
        this.expenseType = expenseType;
        this.transactionType = transactionType;
    }

    public MyExpenses(String date, double expenseAmount, String expenseName, String expenseType, String transactionType, String username) {
        this.date = date;
        this.expenseAmount = expenseAmount;
        this.expenseName = expenseName;
        this.expenseType = expenseType;
        this.transactionType = transactionType;
        this.username = username;
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

    public String getFormatedDate() {
        return Utils.formatDate(date);
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
                "\nexpenseName='" + expenseName + '\'' +
                "\nexpenseAmount=" + expenseAmount +
                "\ndate='" + date + '\'' +
                "\nexpenseType='" + expenseType + '\'' +
                "\ntransactionType='" + transactionType + '\'';
    }
}
