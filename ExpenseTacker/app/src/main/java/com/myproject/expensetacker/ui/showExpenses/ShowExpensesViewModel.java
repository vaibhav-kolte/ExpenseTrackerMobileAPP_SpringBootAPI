package com.myproject.expensetacker.ui.showExpenses;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.retrofit.services.ApiService;
import com.myproject.expensetacker.repository.retrofit.client.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowExpensesViewModel extends ViewModel {
    private static final String TAG = "ShowExpensesViewModel";
    private final MutableLiveData<String> expenses;

    public ShowExpensesViewModel() {
        expenses = new MutableLiveData<>();
        expenses.setValue("Loading expenses");
    }

    public LiveData<String> getText() {
        return expenses;
    }

    public void getExpenses(String username) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<MyExpenses>> call = apiService.getExpenses(username);
        call.enqueue(new Callback<List<MyExpenses>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyExpenses>> call,
                                   @NonNull Response<List<MyExpenses>> response) {
                if (response.isSuccessful()) {
                    List<MyExpenses> repos = response.body();
                    Log.d(TAG, "onResponse: " + repos);
                    StringBuilder result = new StringBuilder();
                    assert repos != null;
                    for (MyExpenses repo : repos) {
                        result.append(repo.toString());
                        result.append("\n\n");
                    }
                    expenses.setValue(String.valueOf(result));
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyExpenses>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
            }
        });
    }
}
