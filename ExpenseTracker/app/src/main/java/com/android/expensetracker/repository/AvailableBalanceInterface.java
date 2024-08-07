package com.android.expensetracker.repository;

public interface AvailableBalanceInterface {
    void onSuccess(double balance);

    void onFailure();
}
