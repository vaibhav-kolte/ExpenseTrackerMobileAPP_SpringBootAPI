package com.myproject.expensetacker.repository.retrofit.services;


import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.AddBalance;
import com.myproject.expensetacker.model.BalanceResponse;
import com.myproject.expensetacker.model.BalanceSummery;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.model.Transaction;

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

    @GET("/transaction/getAll/{username}")
    Call<List<Transaction>> getTransaction(@Path("username") String username);

    @GET("/transaction/balance/{username}")
    Call<BalanceSummery> findBalanceSummery(@Path("username") String username);
}
