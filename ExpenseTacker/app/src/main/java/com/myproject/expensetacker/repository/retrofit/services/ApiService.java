package com.myproject.expensetacker.repository.retrofit.services;

import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.MyExpenses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/login/get/{username}")
    Call<Account> checkUser(@Path("username") String username);

    @POST("/login/add")
    Call<Account> createAccount(@Body Account account);

    @GET("/expense/available-balance/{username}")
    Call<Double> getAvailableBalance(@Path("username") String username);

    @GET("/expense/getAll/{username}")
    Call<List<MyExpenses>> getExpenses(@Path("username") String username);

    @POST("/expense/add")
    Call<Void> addExpense(@Body MyExpenses expenses);

    @PUT("/expense/update/{username}/{id}")
    Call<Void> updateExpense(@Path("username") String username, @Path("id") long id, @Body MyExpenses myExpenses);

    @DELETE("/expense/delete/{username}/{id}")
    Call<Void> deleteExpense(@Path("username") String username, @Path("id") long id);
}
