package com.myproject.expensetacker.repository.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddBalanceInterface;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.BalanceSummeryInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.ExpenseByUsername;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.interfaces.apis.TransactionByUsername;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.AddBalance;
import com.myproject.expensetacker.model.BalanceResponse;
import com.myproject.expensetacker.model.BalanceSummery;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.model.Transaction;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.retrofit.client.RetrofitClient;
import com.myproject.expensetacker.repository.retrofit.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitManager implements ExpenseAPI {


    @Override
    public void availableBalance(String username, CurrentBalance balance, APIException exception) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<BalanceResponse> call = apiService.getAvailableBalance(username);

        call.enqueue(new Callback<BalanceResponse>() {
            @Override
            public void onResponse(@NonNull Call<BalanceResponse> call,
                                   @NonNull Response<BalanceResponse> response) {
                if (response.isSuccessful()) {
                    BalanceResponse repos = response.body();
                    if (repos != null) {
                        balance.currentBalance(repos.getAvailableBalance());
                    } else {
                        exception.apiCalledFailed(response.message());
                    }
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BalanceResponse> call,
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
                if (response.isSuccessful()) {
                    Account login = response.body();
                    assert login != null;
                    loginSuccessfully.loggedInSuccessfully(login);
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
    public void addBalance(AddBalance balance, AddBalanceInterface balanceInterface, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.addBalance(balance);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        balanceInterface.balanceAddedSuccessfully();
                    } else {
                        exception.apiCalledFailed(response.message());
                    }
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
    public void findBalanceSummery(String username, BalanceSummeryInterface summeryInterface, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<BalanceSummery> call = apiService.findBalanceSummery(username);

        call.enqueue(new Callback<BalanceSummery>() {
            @Override
            public void onResponse(@NonNull Call<BalanceSummery> call,
                                   @NonNull Response<BalanceSummery> response) {
                if (response.isSuccessful()) {
                    BalanceSummery summery = response.body();
                    summeryInterface.BalanceSummery(summery);
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BalanceSummery> call,
                                  @NonNull Throwable t) {
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

    @Override
    public void getAllTransactionByUsername(String username, TransactionByUsername transaction, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<Transaction>> call = apiService.getTransaction(username);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call,
                                   @NonNull Response<List<Transaction>> response) {
                if (response.isSuccessful()) {
                    List<Transaction> repos = response.body();
                    transaction.myTransaction(repos);
                } else {
                    exception.apiCalledFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call,
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

}
