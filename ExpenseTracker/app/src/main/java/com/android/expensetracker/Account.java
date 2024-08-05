package com.android.expensetracker;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account {

    private static final String TAG = "Account";

    private final String username;
    private final String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void login(Context context) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Account> call = apiService.checkAccount(1);

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call,
                                   @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    Account repos = response.body();
                    Log.d(TAG, "onResponse: "+repos);
                    Toast.makeText(context, (CharSequence) repos, Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
            }
        });
    }

    public void signIn() {

    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" + "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
