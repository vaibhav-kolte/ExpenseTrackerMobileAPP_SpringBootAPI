package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

public class TypeSummery {
    private String expenseType;
    private Double totalAmount;
    private int icon;

    public TypeSummery(String expenseType, Double totalAmount) {
        this.expenseType = expenseType;
        this.totalAmount = totalAmount;
    }

    public TypeSummery(String expenseType, Double totalAmount, int icon) {
        this.expenseType = expenseType;
        this.totalAmount = totalAmount;
        this.icon = icon;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @NonNull
    @Override
    public String toString() {
        return expenseType + ": " + totalAmount;
    }
}
