package com.android.expensetracker.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.expensetracker.databinding.ActivityAddExpensesBinding;
import com.android.expensetracker.models.MyExpenses;
import com.android.expensetracker.repository.APICall;
import com.android.expensetracker.repository.ApiService;
import com.android.expensetracker.repository.AvailableBalanceInterface;
import com.android.expensetracker.repository.RetrofitClient;
import com.android.expensetracker.utils.ShareData;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpensesActivity extends AppCompatActivity {
    private static final String TAG = "AddExpensesActivity";
    private ActivityAddExpensesBinding binding;
    private Context context;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = AddExpensesActivity.this;
        ShareData shareData = new ShareData(context);
        username = shareData.getString(ShareData.USERNAME, "");

        handleOnClickEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleOnClickEvents() {

        binding.etDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Get the width of the drawable on the right
                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint({"SetTextI18n", "DefaultLocale"})
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.etDate.setText(year + "-" + formatStringTwoDigits(monthOfYear + 1) +
                                        "-" + formatStringTwoDigits(dayOfMonth));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                return true;
            }
            return false;
        });

        binding.btnAdd.setOnClickListener(view -> {
            if (Objects.requireNonNull(binding.etDate.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(binding.etAmount.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(binding.etExpense.getText()).toString().isEmpty()
            ) {
                return;
            }
            String tag = Objects.requireNonNull(binding.etDate.getText()).toString().isEmpty()
                    ? "Extra" : Objects.requireNonNull(binding.etTag.getText()).toString();
            String date = Objects.requireNonNull(binding.etDate.getText()).toString();
            float amount = Float.parseFloat(Objects.requireNonNull(binding.etAmount.getText()).toString());
            String expense = Objects.requireNonNull(binding.etExpense.getText()).toString();

            MyExpenses expenses = new MyExpenses(username, expense, amount, date, tag);
            getMyBudget(expenses);
        });
    }

    @SuppressLint("DefaultLocale")
    private String formatStringTwoDigits(int num) {
        return String.format("%02d", num);
    }

    private void getMyBudget(MyExpenses expenses) {
        APICall apiCall = new APICall(context);
        apiCall.getAvailableBalance(new AvailableBalanceInterface() {
            @Override
            public void onSuccess(double balance) {
                if (expenses.getExpenseAmount() <= balance) {
                    Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    addExpense(expenses);
                } else {
                    Toast.makeText(context, "low balance", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Failed to get budget", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addExpense(MyExpenses expenses) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<Void> call = apiService.addExpense(expenses);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Expense added successfully.");
                } else {
                    Log.e(TAG, "Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Network request failed", t);
            }
        });
    }
}