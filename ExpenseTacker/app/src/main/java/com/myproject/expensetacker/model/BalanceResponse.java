package com.myproject.expensetacker.model;

public class BalanceResponse {

    private int statusCode;
    private double availableBalance;

    public BalanceResponse(int statusCode, double availableBalance) {
        this.statusCode = statusCode;
        this.availableBalance = availableBalance;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }


}
