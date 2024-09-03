package com.myproject.expensetacker.model;

import androidx.annotation.NonNull;

public class MonthlyView {
    private int monthNumber;
    private String monthName;
    private String year;

    public MonthlyView(int monthNumber, String monthName) {
        this.monthNumber = monthNumber;
        this.monthName = monthName;
    }

    public MonthlyView(int monthNumber, String monthName, String year) {
        this.monthNumber = monthNumber;
        this.monthName = monthName;
        this.year = year;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @NonNull
    @Override
    public String toString() {
        return "MonthlyView{" +
                "monthNumber=" + monthNumber +
                ", monthName='" + monthName + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
