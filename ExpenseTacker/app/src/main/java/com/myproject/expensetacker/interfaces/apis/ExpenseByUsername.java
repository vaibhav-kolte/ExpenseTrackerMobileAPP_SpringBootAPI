package com.myproject.expensetacker.interfaces.apis;

import com.myproject.expensetacker.model.MyExpenses;

import java.util.List;

public interface ExpenseByUsername {
    void myExpenses(List<MyExpenses> myExpensesList);
}
