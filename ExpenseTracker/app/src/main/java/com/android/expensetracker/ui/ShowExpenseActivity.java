package com.android.expensetracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.expensetracker.R;
import com.android.expensetracker.databinding.ActivityShowExpenseBinding;
import com.android.expensetracker.models.Account;
import com.android.expensetracker.models.MyExpenses;
import com.android.expensetracker.repository.ApiService;
import com.android.expensetracker.repository.MyExpensesInterface;
import com.android.expensetracker.repository.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowExpenseActivity extends AppCompatActivity {

    private static final String TAG = "ShowExpenseActivity";
    private ActivityShowExpenseBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ShowExpenseActivity.this;

        handleEvents();

    }

    private void handleEvents() {
        getExpenses("vaibhav");
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
                    binding.tvShowExpense.setText(result);
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