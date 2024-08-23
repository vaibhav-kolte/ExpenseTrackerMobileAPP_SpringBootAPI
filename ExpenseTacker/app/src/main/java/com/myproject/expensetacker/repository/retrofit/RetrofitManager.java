package com.myproject.expensetacker.repository.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.DeleteExpense;
import com.myproject.expensetacker.interfaces.apis.ExpenseByUsername;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.interfaces.apis.UpdateExpense;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.ApiError;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.retrofit.client.RetrofitClient;
import com.myproject.expensetacker.repository.retrofit.services.ApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitManager implements ExpenseAPI {

    private static final String TAG = "RetrofitManager";

    @Override
    public void availableBalance(String username, CurrentBalance balance, APIException exception) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Double> call = apiService.getAvailableBalance(username);

        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(@NonNull Call<Double> call,
                                   @NonNull Response<Double> response) {
                if (response.isSuccessful()) {
                    Double availableBalance = response.body();
                    if (availableBalance != null) {
                        balance.currentBalance(availableBalance);
                    } else {
                        exception.apiCalledFailed(response.message());
                    }
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Double> call,
                                  @NonNull Throwable t) {

                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

    @Override
    public void loggedInAccount(String username, LoginSuccessfully loginSuccessfully,
                                APIException exception) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Account> call = apiService.checkUser(username);

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call,
                                   @NonNull Response<Account> response) {
                Log.e(TAG, "onResponse: Login " + response);
                if (response.isSuccessful()) {
                    Account login = response.body();
                    assert login != null;
                    loginSuccessfully.loggedInSuccessfully(login);
                } else {
                    try {
                        Gson gson = new Gson();
                        assert response.errorBody() != null;
                        ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);

                        exception.apiCalledFailed(apiError.getDetail());

                    } catch (IOException e) {
                        exception.apiCalledFailed("Need to check API. Contact developer.");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call,
                                  @NonNull Throwable t) {
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

    @Override
    public void signInAccount(Account account, SigneInSuccessfully inSuccessfully, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Account> call = apiService.createAccount(account);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call,
                                   @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    inSuccessfully.signInSuccessfully();
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call,
                                  @NonNull Throwable t) {
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

    @Override
    public void addExpense(MyExpenses expenses, AddExpenseInterface expenseInterface, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Void> call = apiService.addExpense(expenses);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    expenseInterface.expenseAddedSuccessfully();
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
                                  @NonNull Throwable t) {
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }


    @Override
    public void getAllExpensesByUsername(String username, ExpenseByUsername expense, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<MyExpenses>> call = apiService.getExpenses(username);
        call.enqueue(new Callback<List<MyExpenses>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyExpenses>> call,
                                   @NonNull Response<List<MyExpenses>> response) {
                if (response.isSuccessful()) {
                    List<MyExpenses> repos = response.body();
                    expense.myExpenses(repos);
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyExpenses>> call,
                                  @NonNull Throwable t) {
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

    @Override
    public void updateExpense(MyExpenses myExpenses, UpdateExpense updateExpense, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Void> call = apiService.updateExpense(myExpenses.getUsername(), myExpenses.getId(), myExpenses);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                Log.e("TAG", "onResponse: " + response);
                if (response.isSuccessful()) {
                    updateExpense.expenseUpdated();
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
                                  @NonNull Throwable t) {
                Log.e("TAG", "onFailure: " + t);
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

    @Override
    public void deleteExpense(String username, long id, DeleteExpense deleteExpense, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Void> call = apiService.deleteExpense(username, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                Log.e("TAG", "onResponse: " + response);
                if (response.isSuccessful()) {
                    deleteExpense.expenseDeleted();
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
                                  @NonNull Throwable t) {
                Log.e("TAG", "onFailure: " + t);
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

}
