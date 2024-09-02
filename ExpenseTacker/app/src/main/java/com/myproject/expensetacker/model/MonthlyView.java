package com.myproject.expensetacker.model;


public class MonthlyView {
    private int monthNumber;
    private String monthName;
    private int icon;

    public MonthlyView(int monthNumber, String monthName, int icon) {
        this.monthNumber = monthNumber;
        this.monthName = monthName;
        this.icon = icon;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
