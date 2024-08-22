package com.myproject.expensetacker.ui.showTransaction;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.expensetacker.model.Transaction;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;

import java.util.List;


public class TransactionViewModel extends ViewModel {
    private static final String TAG = "ShowExpensesViewModel";
    private final MutableLiveData<List<Transaction>> transactionList;

    public TransactionViewModel() {
        transactionList = new MutableLiveData<>();
    }

    public LiveData<List<Transaction>> getText() {
        return transactionList;
    }

    public void getTransaction(String username) {

        ExpenseAPI expenseAPI = new ExpenseAPIImpl();
        expenseAPI.getAllTransactionByUsername(username, this.transactionList::setValue, message -> {
            Log.e(TAG, "getTransaction: Exception: " + message);
        });
    }
}
