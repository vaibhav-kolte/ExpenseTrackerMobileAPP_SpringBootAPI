package com.android.expensetracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("products/{id}")
    Call<Account> checkAccount(@Path("id") int id);
}
