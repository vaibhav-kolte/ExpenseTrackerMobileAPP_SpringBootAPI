package com.mycode.myExpenseTracker.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Balance {
    private double availableBalance;
    private double creditCurrentMonth;
    private double debitCurrentMonth;

    public Balance(double availableBalance, double creditCurrentMonth, double debitCurrentMonth) {
        this.availableBalance = availableBalance;
        this.creditCurrentMonth = creditCurrentMonth;
        this.debitCurrentMonth = debitCurrentMonth;
    }
}
