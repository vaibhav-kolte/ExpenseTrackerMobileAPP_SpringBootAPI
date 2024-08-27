package com.myproject.expensetacker.model;


import androidx.annotation.NonNull;

public class ExpenseSummary {
    private double totalDebit;
    private double totalCredit;
    private String monthYear;

    public ExpenseSummary(double totalDebit, double totalCredit, String monthYear) {
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.monthYear = monthYear;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    @NonNull
    @Override
    public String toString() {
        return "ExpenseSummary{" +
                "totalDebit=" + totalDebit +
                ", totalCredit=" + totalCredit +
                ", monthYear='" + monthYear + '\'' +
                '}';
    }
}
