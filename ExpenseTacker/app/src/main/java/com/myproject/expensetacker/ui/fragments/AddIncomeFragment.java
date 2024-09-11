package com.myproject.expensetacker.ui.fragments;

import static com.myproject.expensetacker.utils.Utils.getCurrentDate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myproject.expensetacker.databinding.FragmentAddIncomeBinding;
import com.myproject.expensetacker.interfaces.DatePicker;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AddIncomeFragment extends Fragment {
    private static final String TAG = "AddIncomeFragment";
    private FragmentAddIncomeBinding binding;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false);
        context = getContext();
        handleOnClickEvents();
        clearField();
        return binding.getRoot();
    }

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
        binding.btnAdd.setOnClickListener(view -> {
            String amountInString = Objects.requireNonNull(binding.etAmount.getText()).toString();
            if (TextUtils.isEmpty(amountInString)) {
                binding.textFieldAmount.setError("Add amount");
                return;
            }
            double amount = Double.parseDouble(amountInString);
            String date = binding.tvTodayDate.getText().toString();
            ShareData shareData = new ShareData(context);
            String username = shareData.getString(ShareData.USERNAME, "");

            MyExpenses myExpenses = new MyExpenses(username, "", amount, date,
                    "", "CREDIT");
            addBalance(myExpenses);

        });
    }

    private void addBalance(MyExpenses myExpenses) {
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        expenseAPIs.addExpense(myExpenses, () -> {

        }, message -> {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
        });
    }

    private LocalDate getPreviousDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.minusDays(1);
    }

    private LocalDate getNextDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.plusDays(1);
    }

    private void clearField() {
        binding.tvTodayDate.setText(getCurrentDate());
        binding.etAmount.setText("");
    }
}