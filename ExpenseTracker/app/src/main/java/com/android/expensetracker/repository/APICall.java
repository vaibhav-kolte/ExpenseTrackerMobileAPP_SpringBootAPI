package com.android.expensetracker.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.expensetracker.models.AddBalance;
import com.android.expensetracker.models.BalanceResponse;
import com.android.expensetracker.utils.ShareData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICall {
    private static final String TAG = "APICall";

    private final Context context;

    public APICall(Context context) {
        this.context = context;
    }

    public void getAvailableBalance(AvailableBalanceInterface balanceInterface) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        ShareData shareData = new ShareData(context);
        String username = shareData.getString(ShareData.USERNAME, "");
        if (TextUtils.isEmpty(username)) return;
        Call<BalanceResponse> call = apiService.getAvailableBalance(username);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(@NonNull Call<BalanceResponse> call,
                                   @NonNull Response<BalanceResponse> response) {
                if (response.isSuccessful()) {
                    BalanceResponse repos = response.body();
                    Log.d(TAG, "onResponse: " + repos);
                    if (repos != null) {
                        balanceInterface.onSuccess(repos.getAvailableBalance());
                    } else {
                        balanceInterface.onFailure();
                    }
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                    balanceInterface.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BalanceResponse> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
                balanceInterface.onFailure();
            }
        });

    }

    public void addBalance(AddBalance addBalance, APICallInterface callInterface) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.addBalance(addBalance);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        System.out.println("Request succeeded with status code 200");
                        callInterface.onSuccess();
                    } else {
                        callInterface.onFailure();
                    }
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                    callInterface.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
                callInterface.onFailure();
            }
        });

    }
}
