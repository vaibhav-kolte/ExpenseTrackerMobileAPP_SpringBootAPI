package com.myproject.expensetacker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.myproject.expensetacker.databinding.ActivityAddExpensesBinding;
import com.myproject.expensetacker.interfaces.DatePicker;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPIs;
import com.myproject.expensetacker.repository.retrofit.RetrofitManager;
import com.myproject.expensetacker.utils.ShareData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddExpensesActivity extends AppCompatActivity {
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
        AddExpensesActivity.this.setTitle("Add Expense");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        handleOnClickEvents();

        clearFiled();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleOnClickEvents() {

        binding.etDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                DatePicker datePicker = new DatePicker(context);
                datePicker.getDate(date -> binding.etDate.setText(date));

                return true;
            }
            return false;
        });

        binding.btnSave.setOnClickListener(view -> {
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
            addExpense(expenses);
        });
    }

    private void addExpense(MyExpenses expenses) {
        ExpenseAPIs expenseAPIs = new RetrofitManager();
        expenseAPIs.addExpense(expenses, () -> {
            Toast.makeText(context, "Expense Added successfully.", Toast.LENGTH_SHORT).show();
            finish();
        }, message -> {

        });
    }


    private void clearFiled() {
        binding.etDate.setText(getCurrentDate());
        binding.etExpense.setText("");
        binding.etAmount.setText("");
        binding.etTag.setText("");
        String[] timeArray = new String[]{"Select Tag", "My Self", "Bike", "Home", "Travel", "Hotel"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timeArray);
        binding.etTag.setText(arrayAdapter.getItem(0));
        binding.etTag.setAdapter(arrayAdapter);
    }

    @NonNull
    private String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}