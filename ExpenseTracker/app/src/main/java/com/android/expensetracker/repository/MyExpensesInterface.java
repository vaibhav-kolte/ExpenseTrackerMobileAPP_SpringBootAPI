package com.android.expensetracker.repository;

import com.android.expensetracker.models.MyExpenses;

public interface MyExpensesInterface {
    void onSuccess(MyExpenses myExpenses);

    void onFailure();
}
