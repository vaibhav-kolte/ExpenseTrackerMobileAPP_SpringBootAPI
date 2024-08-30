package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

public class TypeSummery {
    private String expenseType;
    private Double totalAmount;

    public TypeSummery(String expenseType, Double totalAmount) {
        this.expenseType = expenseType;
        this.totalAmount = totalAmount;
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

    @NonNull
    @Override
    public String toString() {
        return expenseType + ": " + totalAmount;
    }
}
