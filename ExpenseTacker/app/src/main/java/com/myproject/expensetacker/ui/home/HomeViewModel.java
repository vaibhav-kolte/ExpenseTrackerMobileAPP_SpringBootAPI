package com.myproject.expensetacker.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.myproject.expensetacker.model.BalanceSummery;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;

public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";
    private final MutableLiveData<Double> myBudget;
    private final MutableLiveData<BalanceSummery> balanceSummery;

    public HomeViewModel(Application application) {
        super(application);

        myBudget = new MutableLiveData<>();
        balanceSummery = new MutableLiveData<>();
    }

    public MutableLiveData<Double> getMyBudget() {
        return myBudget;
    }

    public MutableLiveData<BalanceSummery> getBalanceSummery() {
        return balanceSummery;
    }

    public void getMyBudgetUsingAPI() {

        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();

        ShareData shareData = new ShareData(getApplication().getApplicationContext());

        String username = shareData.getString(ShareData.USERNAME, "");

        expenseAPIs.availableBalance(username, myBudget::setValue, message -> {
            Log.e(TAG, "getMyBudgetUsingAPI: Exception: " + message);
            myBudget.setValue(0.0);
        });
    }

}