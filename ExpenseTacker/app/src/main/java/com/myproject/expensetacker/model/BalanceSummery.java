package com.myproject.expensetacker.model;

public class BalanceSummery {
    private double availableBalance;
    private double creditCurrentMonth;
    private double debitCurrentMonth;

    public BalanceSummery(double availableBalance, double creditCurrentMonth, double debitCurrentMonth) {
        this.availableBalance = availableBalance;
        this.creditCurrentMonth = creditCurrentMonth;
        this.debitCurrentMonth = debitCurrentMonth;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getCreditCurrentMonth() {
        return creditCurrentMonth;
    }

    public void setCreditCurrentMonth(double creditCurrentMonth) {
        this.creditCurrentMonth = creditCurrentMonth;
    }

    public double getDebitCurrentMonth() {
        return debitCurrentMonth;
    }

    public void setDebitCurrentMonth(double debitCurrentMonth) {
        this.debitCurrentMonth = debitCurrentMonth;
    }
}
