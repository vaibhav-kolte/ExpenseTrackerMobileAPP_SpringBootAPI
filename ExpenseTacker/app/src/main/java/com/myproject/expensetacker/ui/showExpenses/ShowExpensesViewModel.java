package com.myproject.expensetacker.ui.showExpenses;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;

import java.util.List;

public class ShowExpensesViewModel extends ViewModel {
    private static final String TAG = "ShowExpensesViewModel";
    private final MutableLiveData<List<MyExpenses>> expensesList;
//    private final Context context;

    public ShowExpensesViewModel(Context context) {
        expensesList = new MutableLiveData<>();
//        this.context = context;
        ShareData shareData = new ShareData(context);
        String username = shareData.getString(ShareData.USERNAME,"");
        getExpenses(username);
    }

    public LiveData<List<MyExpenses>> getText() {
        return expensesList;
    }

    public void getExpenses(String username) {
        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance();
        expenseAPI.getAllExpensesByUsername(username, this.expensesList::setValue, message -> {
            Log.e(TAG, "getExpenses: Exception: " + message);
        });
    }
}
