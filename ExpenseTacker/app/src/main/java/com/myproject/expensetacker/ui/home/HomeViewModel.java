package com.myproject.expensetacker.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myproject.expensetacker.repository.ExpenseAPIs;
import com.myproject.expensetacker.repository.retrofit.RetrofitManager;
import com.myproject.expensetacker.utils.ShareData;

public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";
    private final MutableLiveData<String> mText;
    private final MutableLiveData<Double> myBudget;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        myBudget = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Double> getMyBudget() {
        return myBudget;
    }

    public void getMyBudgetUsingAPI() {

        ExpenseAPIs expenseAPIs = new RetrofitManager();

        ShareData shareData = new ShareData(getApplication().getApplicationContext());

        String username = shareData.getString(ShareData.USERNAME, "");

        expenseAPIs.availableBalance(username, myBudget::setValue, message -> {
            Log.e(TAG, "getMyBudgetUsingAPI: Exception: " + message);
            myBudget.setValue(0.0);
        });
    }
}