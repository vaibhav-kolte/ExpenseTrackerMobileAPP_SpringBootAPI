package com.myproject.expensetacker.ui;

import static com.myproject.expensetacker.utils.Constant.USED_DATABASE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.myproject.expensetacker.databinding.ActivityAddExpensesBinding;
import com.myproject.expensetacker.interfaces.DatePicker;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.Constant;
import com.myproject.expensetacker.utils.PrintLog;
import com.myproject.expensetacker.utils.ShareData;
import com.myproject.expensetacker.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddExpensesActivity extends AppCompatActivity {
    private static final String TAG = "AddExpensesActivity";
    private ActivityAddExpensesBinding binding;
    private Context context;
    private String username;
    private MyExpenses myExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = AddExpensesActivity.this;
        ShareData shareData = new ShareData(context);
        username = shareData.getString(ShareData.USERNAME, "");


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        handleOnClickEvents();

        clearFiled();

        Intent i = getIntent();
        myExpenses = (MyExpenses) i.getSerializableExtra("EXPENSE_OBJECT");
        if (myExpenses == null) {
            AddExpensesActivity.this.setTitle("Add Expense");
        } else {
            AddExpensesActivity.this.setTitle("Update Expense");
            updateResource(myExpenses);
        }
        Log.e(TAG, "onCreate: MyExpense: " + myExpenses);
    }

    private void updateResource(MyExpenses myExpenses) {
        binding.etDate.setText(Utils.formatDate(myExpenses.getDate()));
        binding.etExpense.setText(myExpenses.getExpenseName());
        binding.etAmount.setText(String.valueOf(myExpenses.getExpenseAmount()));
        updateExpenseType(myExpenses.getExpenseType());
        binding.btnSave.setVisibility(View.GONE);
        binding.btnSaveNext.setVisibility(View.GONE);
        binding.btnUpdate.setVisibility(View.VISIBLE);
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
            try {
                MyExpenses expenses = getMyExpenses();
                addExpense(expenses);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnUpdate.setOnClickListener(view -> {
            try {
                MyExpenses expense = getMyExpenses();
                expense.setId(myExpenses.getId());
                updateExpense(expense);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSaveNext.setOnClickListener(view -> {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
        });
    }

    private double getFormatedAmount(double amount) {
        BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void addExpense(MyExpenses expenses) {
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        expenseAPIs.addExpense(expenses, () -> {
            Toast.makeText(context, "Expense Added successfully.", Toast.LENGTH_SHORT).show();
            finish();
            PrintLog.infoLog(TAG, expenses.getExpenseName() + " expense added successfully");
        }, message -> {
            PrintLog.errorLog(TAG, expenses.getExpenseName() + " failed to add expense.");
        });
    }

    private void updateExpense(MyExpenses myExpenses) {
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        expenseAPIs.updateExpense(myExpenses, () -> {
            Toast.makeText(context, "Expense Updated successfully.", Toast.LENGTH_SHORT).show();
            finish();
        }, message -> {
            Log.e(TAG, "updateExpense: Exception: " + message);
        });
    }


    private void clearFiled() {
        binding.etDate.setText(getCurrentDate());
        binding.etExpense.setText("");
        binding.etAmount.setText("");
        binding.etTag.setText("");
        updateExpenseType("Select Tag");
    }

    private void updateExpenseType(String selectTag) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Constant.expenseType);
        binding.etTag.setText(selectTag);
        binding.etTag.setAdapter(arrayAdapter);
    }

    @NonNull
    private String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    private MyExpenses getMyExpenses() throws Exception {
        if (Objects.requireNonNull(binding.etDate.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.etAmount.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.etExpense.getText()).toString().isEmpty()
        ) {
            throw new Exception("Filed should not empty!!!");
        }
        String tag = Objects.requireNonNull(binding.etDate.getText()).toString().isEmpty()
                ? "Extra" : Objects.requireNonNull(binding.etTag.getText()).toString();
        String date = Objects.requireNonNull(binding.etDate.getText()).toString();
        double amount = getFormatedAmount(Float.parseFloat(Objects.requireNonNull(binding.etAmount.getText()).toString()));
        String expense = Objects.requireNonNull(binding.etExpense.getText()).toString();
        return new MyExpenses(username, expense, amount, date, tag, "DEBIT");
    }


}