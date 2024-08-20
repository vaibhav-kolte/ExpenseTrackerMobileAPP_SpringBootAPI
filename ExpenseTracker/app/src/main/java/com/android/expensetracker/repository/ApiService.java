package com.android.expensetracker.repository;

import com.android.expensetracker.models.Account;
import com.android.expensetracker.models.AddBalance;
import com.android.expensetracker.models.BalanceResponse;
import com.android.expensetracker.models.MyExpenses;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/login/get/{username}")
    Call<Account> checkUser(@Path("username") String username);

    @POST("/login/add")
    Call<Account> createAccount(@Body Account account);

    @GET("/transaction/available-balance/{username}")
    Call<BalanceResponse> getAvailableBalance(@Path("username") String username);

    @POST("/transaction/add")
    Call<Void> addBalance(@Body AddBalance addBalance);

    @GET("/expense/getAll/{username}")
    Call<List<MyExpenses>> getExpenses(@Path("username") String username);

    @POST("/expense/add")
    Call<Void> addExpense(@Body MyExpenses expenses);
}
