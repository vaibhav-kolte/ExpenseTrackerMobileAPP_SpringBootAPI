package com.myproject.expensetacker.ui.showTransaction;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.expensetacker.model.Transaction;
import com.myproject.expensetacker.repository.retrofit.services.ApiService;
import com.myproject.expensetacker.repository.retrofit.client.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionViewModel extends ViewModel {
    private static final String TAG = "ShowExpensesViewModel";
    private final MutableLiveData<String> transaction;

    public TransactionViewModel() {
        transaction = new MutableLiveData<>();
        transaction.setValue("Loading expenses");
    }

    public LiveData<String> getText() {
        return transaction;
    }

    public void getTransaction(String username) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<Transaction>> call = apiService.getTransaction(username);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call,
                                   @NonNull Response<List<Transaction>> response) {
                if (response.isSuccessful()) {
                    List<Transaction> repos = response.body();
                    Log.d(TAG, "onResponse: " + repos);
                    StringBuilder result = new StringBuilder();
                    assert repos != null;
                    for (Transaction repo : repos) {
                        result.append(repo.toString());
                        result.append("\n\n");
                    }
                    transaction.setValue(String.valueOf(result));
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
            }
        });
    }
}
