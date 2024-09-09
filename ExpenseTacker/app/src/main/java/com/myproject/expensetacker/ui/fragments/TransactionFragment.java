package com.myproject.expensetacker.ui.fragments;

import static com.myproject.expensetacker.utils.Utils.getCurrentDate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.FragmentTransactionBinding;
import com.myproject.expensetacker.interfaces.DatePicker;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.PrintLog;
import com.myproject.expensetacker.utils.ShareData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class TransactionFragment extends Fragment {
    private static final String TAG = "TransactionFragment";
    private FragmentTransactionBinding binding;
    private Context context;
    private TextView selectedType;
    private String username;
    private MyExpenses myExpenses;

    public TransactionFragment() {
    }

    public TransactionFragment(MyExpenses myExpenses) {
        this.myExpenses = myExpenses;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        context = getContext();
        ShareData shareData = new ShareData(context);
        username = shareData.getString(ShareData.USERNAME, "");
        handleOnClickEvents();
        clearFields();
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleOnClickEvents() {
        binding.iconLeftDate.setOnClickListener(view ->
                binding.tvTodayDate.setText(String.valueOf(
                        getPreviousDate(binding.tvTodayDate.getText().toString()))));

        binding.iconRightDate.setOnClickListener(view ->
                binding.tvTodayDate.setText(String.valueOf(
                        getNextDate(binding.tvTodayDate.getText().toString()))));

        binding.tvTodayDate.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker(context);
            datePicker.getDate(date -> binding.tvTodayDate.setText(date));
        });
        binding.tvMySelf.setOnClickListener(view -> updateSelectedType(binding.tvMySelf));
        binding.tvHome.setOnClickListener(view -> updateSelectedType(binding.tvHome));
        binding.tvRent.setOnClickListener(view -> updateSelectedType(binding.tvRent));
        binding.tvBike.setOnClickListener(view -> updateSelectedType(binding.tvBike));
        binding.tvRecharge.setOnClickListener(view -> updateSelectedType(binding.tvRecharge));
        binding.tvMedical.setOnClickListener(view -> updateSelectedType(binding.tvMedical));
        binding.tvTravel.setOnClickListener(view -> updateSelectedType(binding.tvTravel));
        binding.tvOutsideFood.setOnClickListener(view -> updateSelectedType(binding.tvOutsideFood));
        binding.tvMember.setOnClickListener(view -> updateSelectedType(binding.tvMember));

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
    }

    private void updateExpense(MyExpenses myExpenses) {
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        expenseAPIs.updateExpense(myExpenses, () -> {
            Toast.makeText(context, "Expense Updated successfully.", Toast.LENGTH_SHORT).show();
            this.myExpenses = null;
            clearFields();
        }, message -> PrintLog.errorLog(TAG, "updateExpense: Exception: " + message));
    }

    private void addExpense(MyExpenses expenses) {
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        System.out.println("Expense " + expenses);
        expenseAPIs.addExpense(expenses, () -> {
            Toast.makeText(context, "Expense Added successfully.", Toast.LENGTH_SHORT).show();
            PrintLog.infoLog(TAG, expenses.getExpenseName() + " expense added successfully");
            clearFields();
        }, message -> {
            PrintLog.errorLog(TAG, expenses.getExpenseName() + " failed to add expense. " + message);
        });
    }

    @NonNull
    private MyExpenses getMyExpenses() throws Exception {
        if (Objects.requireNonNull(binding.tvTodayDate.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.etAmount.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.etExpense.getText()).toString().isEmpty()
        ) {
            throw new Exception("Filed should not empty!!!");
        }
        String tag = selectedType.getText().toString();
        String date = Objects.requireNonNull(binding.tvTodayDate.getText()).toString();
        double amount = getFormatedAmount(Float.parseFloat(Objects.requireNonNull(binding.etAmount.getText()).toString()));
        String expense = Objects.requireNonNull(binding.etExpense.getText()).toString();
        return new MyExpenses(username, expense, amount, date, tag, "DEBIT");
    }

    private double getFormatedAmount(double amount) {
        BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void updateSelectedType(TextView textView) {
        if (selectedType != null) {
            selectedType.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border));
        }
        selectedType = textView;
        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_type_bg));
    }

    private LocalDate getPreviousDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.minusDays(1);
    }

    private LocalDate getNextDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.plusDays(1);
    }

    private void clearFields() {
        if (myExpenses != null) {
            binding.tvTodayDate.setText(myExpenses.getFormatedDate());
            binding.etExpense.setText(myExpenses.getExpenseName());
            binding.etAmount.setText(String.valueOf(myExpenses.getExpenseAmount()));
            if (binding.tvMySelf.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvMySelf);
            if (binding.tvHome.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvHome);
            if (binding.tvRent.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvRent);
            if (binding.tvBike.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvBike);
            if (binding.tvRecharge.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvRecharge);
            if (binding.tvMedical.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvMedical);
            if (binding.tvTravel.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvTravel);
            if (binding.tvOutsideFood.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvOutsideFood);
            if (binding.tvMember.getText().equals(myExpenses.getExpenseType()))
                updateType(binding.tvMember);
            binding.btnSave.setVisibility(View.GONE);
            binding.btnUpdate.setVisibility(View.VISIBLE);
        } else {
            binding.tvTodayDate.setText(getCurrentDate());
            updateType(binding.tvMySelf);
            binding.etExpense.setText("");
            binding.etAmount.setText("");
            binding.btnSave.setVisibility(View.VISIBLE);
            binding.btnUpdate.setVisibility(View.GONE);
        }
    }

    private void updateType(@NonNull TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_type_bg));
        selectedType = textView;
    }
}