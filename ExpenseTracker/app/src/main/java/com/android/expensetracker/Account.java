package com.android.expensetracker;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account {

    private static final String TAG = "Account";

    private final String username;
    private final String myPassword;

    public Account(String username, String myPassword) {
        this.username = username;
        this.myPassword = myPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void login(String username, LoginInterface loginInterface) {
        System.out.println("calling 1");
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        System.out.println("calling 2");
        Call<Account> call = apiService.checkUser(username);
        System.out.println("calling 3");
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call,
                                   @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    Account repos = response.body();
                    Log.d(TAG, "onResponse: " + repos);
                    loginInterface.onSuccessful(repos);
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                    loginInterface.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
                loginInterface.onFailure();
            }
        });
    }

    public void signIn(Account account, SignInInterface signInInterface) {
        System.out.println("calling 1");
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        System.out.println("calling 2");
        Call<Account> call = apiService.createAccount(account);
        System.out.println("calling 3");
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call,
                                   @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    Account repos = response.body();
                    Log.d(TAG, "onResponse: " + repos);
                    signInInterface.onSuccessful(repos);
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                    signInInterface.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
                signInInterface.onFailure();
            }
        });
    }

    public interface LoginInterface {
        void onSuccessful(Account account);

        void onFailure();
    }

    public interface SignInInterface {
        void onSuccessful(Account account);

        void onFailure();
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" + "username='" + username + '\'' +
                ", myPassword='" + myPassword + '\'' +
                '}';
    }
}
