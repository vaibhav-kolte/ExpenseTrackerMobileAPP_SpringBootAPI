package com.mycode.myExpenseTracker.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseTypeSummery {
    private String expenseType;
    private Double totalAmount;
}
