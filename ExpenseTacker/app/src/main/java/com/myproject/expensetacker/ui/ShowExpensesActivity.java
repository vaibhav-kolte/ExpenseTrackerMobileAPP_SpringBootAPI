package com.myproject.expensetacker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.myproject.expensetacker.adapter.ExpenseAdapter;
import com.myproject.expensetacker.adapter.MonthlyViewAdapter;
import com.myproject.expensetacker.databinding.ActivityShowExpensesBinding;
import com.myproject.expensetacker.interfaces.SelectedMonth;
import com.myproject.expensetacker.model.MonthlyView;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;


import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowExpensesActivity extends AppCompatActivity implements SelectedMonth {
    private static final String TAG = "ShowExpensesActivity";
    private ActivityShowExpensesBinding binding;
    private Context context;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ShowExpensesActivity.this;
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        handleOnCLick();

        showMonths();

        ShareData shareData = new ShareData(context);
        username = shareData.getString(ShareData.USERNAME, "");
        getExpenses(username);

    }

    private void showMonths() {
        List<MonthlyView> monthlyViews = getMonthlyViews();
        MonthlyViewAdapter adapter = new MonthlyViewAdapter(monthlyViews, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(binding.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @NonNull
    private List<MonthlyView> getMonthlyViews() {
        List<MonthlyView> monthlyViews = new ArrayList<>();
        monthlyViews.add(new MonthlyView(-1, "All", "0000"));
        String yearRange = getCurrentFinancialYear(); // Example input string

        String[] years = yearRange.split(" ");

        String startYear = years[0];
        String endYear = years[1];

        LocalDate startDate = LocalDate.of(Integer.parseInt(startYear), 4, 1); // April 2024
        LocalDate endDate = LocalDate.of(Integer.parseInt(endYear), 3, 31);  // March 2025

        DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM"); // Format: "Apr 2024"
        DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy"); // Format: "Apr 2024"

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            monthlyViews.add(new MonthlyView(Integer.parseInt(currentDate.format(formatterMonth)),
                    getMonthName(Integer.parseInt(currentDate.format(formatterMonth))),
                    currentDate.format(formatterYear))
            );
            currentDate = currentDate.plusMonths(1);
        }
        return monthlyViews;
    }

    public String getMonthName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            return "Invalid month number";
        }
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] shortMonths = dfs.getShortMonths();
        return shortMonths[monthNumber - 1];
    }

    @NonNull
    public static String getCurrentFinancialYear() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        LocalDate startOfCurrentFinancialYear = LocalDate.of(currentYear, 4, 1);
        LocalDate endOfCurrentFinancialYear = LocalDate.of(currentYear + 1, 3, 31);

        if (today.isBefore(startOfCurrentFinancialYear)) {
            startOfCurrentFinancialYear = LocalDate.of(currentYear - 1, 4, 1);
            endOfCurrentFinancialYear = LocalDate.of(currentYear, 3, 31);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return startOfCurrentFinancialYear.format(formatter) + " " + endOfCurrentFinancialYear.format(formatter);
    }

    private void handleOnCLick() {


    }

    public void getExpenses(String username) {
        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance();
        expenseAPI.getAllExpensesByUsername(username, this::updateRecyclerView, message -> {
            Log.e(TAG, "getExpenses: Exception: " + message);
        });
    }

    @Override
    public void selectedMonth(@NonNull MonthlyView monthlyView) {
        if (monthlyView.getMonthNumber() == -1) {
            getExpenses(username);
        } else {
            @SuppressLint("DefaultLocale")
            String currentMonthStartDate = monthlyView.getYear() + "-" +
                    String.format("%02d", monthlyView.getMonthNumber()) + "-01";
            System.out.println("Current month date: " + currentMonthStartDate);
            String nextMonthStartDate = getNextMonthFirstDate(currentMonthStartDate);
            System.out.println("Next month date: " + nextMonthStartDate);
            showExpensesByMonth(username, currentMonthStartDate, nextMonthStartDate);
        }

    }

    private void showExpensesByMonth(String username, String startDate, String endDate) {
        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance();
        expenseAPI.getExpenseByDuration(username, startDate, endDate,
                this::updateRecyclerView, message -> {
                    Log.e(TAG, "showExpensesByMonth: Exception: " + message);
                });
    }

    private void updateRecyclerView(@NonNull List<MyExpenses> myExpensesList) {
        if (!myExpensesList.isEmpty()) {
            binding.expensesRecyclerView.setVisibility(View.VISIBLE);
            binding.noExpense.setVisibility(View.GONE);
            ExpenseAdapter adapter = new ExpenseAdapter(myExpensesList);
            binding.expensesRecyclerView.setHasFixedSize(true);
            binding.expensesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            binding.expensesRecyclerView.setAdapter(adapter);
        } else {
            binding.expensesRecyclerView.setVisibility(View.GONE);
            binding.noExpense.setVisibility(View.VISIBLE);
        }

    }

    public String getNextMonthFirstDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalDate nextMonthFirstDay = parsedDate.plusMonths(1).withDayOfMonth(1);
            return nextMonthFirstDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Handle Back button
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}