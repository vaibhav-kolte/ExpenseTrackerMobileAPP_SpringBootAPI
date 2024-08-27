package com.myproject.expensetacker.repository.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.DeleteExpense;
import com.myproject.expensetacker.interfaces.apis.ExpenseByUsername;
import com.myproject.expensetacker.interfaces.apis.ExpenseSummeryResponse;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.MyLogin;
import com.myproject.expensetacker.interfaces.apis.ProfilePhotoAdded;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.interfaces.apis.UpdateExpense;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.ApiError;
import com.myproject.expensetacker.model.ExpenseSummary;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.retrofit.client.RetrofitClient;
import com.myproject.expensetacker.repository.retrofit.services.ApiService;
import com.myproject.expensetacker.utils.PrintLog;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitManager implements ExpenseAPI {

    private static final String TAG = "RetrofitManager";

    @Override
    public void uploadProfilePhoto(String username, File file, ProfilePhotoAdded profilePhotoAdded, APIException exception) {
        if (file == null) {
            PrintLog.errorLog(TAG, "File is null, aborting upload.");
            return;
        }

        if (file.exists() && file.length() > 0) {
            RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<ResponseBody> call = apiService.uploadProfilePhoto(username, body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call,
                                       @NonNull Response<ResponseBody> response) {
                    System.out.println(response.code());
                    if (response.isSuccessful()) {
                        PrintLog.infoLog(TAG, "Image uploaded successfully");
                    } else {
                        PrintLog.errorLog(TAG, "Upload failed");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call,
                                      @NonNull Throwable t) {
                    PrintLog.errorLog(TAG, "Upload failed: " + t.getMessage());
                }
            });
        } else {
            PrintLog.errorLog(TAG, "File is null, doesn't exist, or is empty.");
        }
    }

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
                    if (login != null) {
                        loginSuccessfully.loggedInSuccessfully(login);
                    } else {
                        exception.apiCalledFailed("Login response is null");
                    }
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

    @Override
    public void getMyAccount(String username, MyLogin myLogin, APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Account> call = apiService.checkUser(username);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call,
                                   @NonNull Response<Account> response) {
                Log.e(TAG, "onResponse: Login " + response);
                if (response.isSuccessful()) {
                    Account login = response.body();
                    if (login != null) {
                        myLogin.getMyLogin(login);
                    } else {
                        exception.apiCalledFailed("Login response is null");
                    }
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
    public void getExpenseSummeryInCurrentMonth(String username, ExpenseSummeryResponse expenseSummary,
                                                APIException exception) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ExpenseSummary> call = apiService.getExpenseSummeryInCurrentMonth(username);
        call.enqueue(new Callback<ExpenseSummary>() {
            @Override
            public void onResponse(@NonNull Call<ExpenseSummary> call,
                                   @NonNull Response<ExpenseSummary> response) {
                Log.e(TAG, "onResponse: Login " + response);
                if (response.isSuccessful()) {
                    ExpenseSummary expenseSummaryResponse = response.body();
                    if (expenseSummaryResponse != null) {
                        expenseSummary.getExpenseSummery(expenseSummaryResponse);
                    } else {
                        exception.apiCalledFailed("ExpenseSummary response is null");
                    }
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
            public void onFailure(@NonNull Call<ExpenseSummary> call,
                                  @NonNull Throwable t) {
                exception.apiCalledFailed(t.getMessage());
            }
        });
    }

}
