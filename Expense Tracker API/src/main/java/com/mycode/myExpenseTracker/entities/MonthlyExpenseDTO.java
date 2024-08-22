package com.mycode.myExpenseTracker.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthlyExpenseDTO {
    private String month;
    private Double totalCredit;
    private Double totalDebit;
    private Double totalExpense;


    public MonthlyExpenseDTO(String month, Double totalCredit, Double totalDebit, Double totalExpense) {
        this.month = month;
        this.totalCredit = totalCredit;
        this.totalDebit = totalDebit;
        this.totalExpense = totalExpense;
    }

}
