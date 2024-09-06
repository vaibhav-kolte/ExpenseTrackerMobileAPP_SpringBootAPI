package com.mycode.myExpenseTracker.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class DailyTransactionSummary {
    private LocalDate transactionDate;
    private Double totalIncome;
    private Double totalExpenses;

    public DailyTransactionSummary(LocalDate transactionDate, Double totalIncome, Double totalExpenses) {
        this.transactionDate = transactionDate;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
    }
}
