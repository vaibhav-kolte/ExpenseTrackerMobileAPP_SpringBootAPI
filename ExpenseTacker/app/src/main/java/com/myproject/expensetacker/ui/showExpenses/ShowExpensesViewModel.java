package com.myproject.expensetacker.ui.showExpenses;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;

import java.util.List;

public class ShowExpensesViewModel extends ViewModel {
    private static final String TAG = "ShowExpensesViewModel";
    private final MutableLiveData<List<MyExpenses>> expensesList;

    public ShowExpensesViewModel() {
        expensesList = new MutableLiveData<>();
    }

    public LiveData<List<MyExpenses>> getText() {
        return expensesList;
    }

    public void getExpenses(String username) {
        ExpenseAPI expenseAPI = new ExpenseAPIImpl();
        expenseAPI.getAllExpensesByUsername(username, expensesList::setValue, message -> {
            Log.e(TAG, "getExpenses: Exception: " + message);
        });
    }
}
