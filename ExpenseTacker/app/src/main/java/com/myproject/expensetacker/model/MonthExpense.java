package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

public class MonthExpense {
    private String date;
    private double income;
    private double expense;
    private boolean isCurrentMonthDay;

    public MonthExpense() {
    }

    public MonthExpense(String date, boolean isCurrentMonthDay) {
        this.date = date;
        income = 0;
        expense = 0;
        this.isCurrentMonthDay = isCurrentMonthDay;
    }

    public MonthExpense(String date, double income, double expense) {
        this.date = date;
        this.income = income;
        this.expense = expense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public boolean isCurrentMonthDay() {
        return isCurrentMonthDay;
    }

    public void setCurrentMonthDay(boolean currentMonthDay) {
        isCurrentMonthDay = currentMonthDay;
    }

    @NonNull
    @Override
    public String toString() {
        return "MonthExpense{" +
                "date='" + date + '\'' +
                ", income=" + income +
                ", expense=" + expense +
                ", isCurrentMonthDay=" + isCurrentMonthDay +
                '}';
    }
}
