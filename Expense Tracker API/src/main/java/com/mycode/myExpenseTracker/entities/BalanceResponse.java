package com.mycode.myExpenseTracker.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BalanceResponse {

    private int statusCode;
    private double availableBalance;

    public BalanceResponse(int statusCode, double availableBalance) {
        this.statusCode = statusCode;
        this.availableBalance = availableBalance;
    }

}

