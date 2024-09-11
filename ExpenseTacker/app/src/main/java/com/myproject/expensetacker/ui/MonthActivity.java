package com.myproject.expensetacker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.GridSpacingItemDecoration;
import com.myproject.expensetacker.adapter.MonthViewAdapter;
import com.myproject.expensetacker.databinding.ActivityMonthBinding;
import com.myproject.expensetacker.model.MonthExpense;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthActivity extends AppCompatActivity {

    private static final String TAG = "MonthActivity";
    private ActivityMonthBinding binding;
    private Context context;
    private LocalDate currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = MonthActivity.this;
        currentDate = LocalDate.now();

        handleClickEvent();
        updateMonth();
        int dividerSpace = 2; // Set the space between dividers
        RecyclerView.ItemDecoration itemDecoration = new GridSpacingItemDecoration(dividerSpace);
        binding.recyclerView.addItemDecoration(itemDecoration);
    }

    private void handleClickEvent() {
        binding.iconLeftDate.setOnClickListener(view -> {
            currentDate = currentDate.minusMonths(1);
            updateMonth();
        });

        binding.iconRightDate.setOnClickListener(view -> {
            currentDate = currentDate.plusMonths(1);
            updateMonth();
        });
    }

    private void updateMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
        binding.tvTodayDate.setText(formattedDate);
        List<MonthExpense> monthExpenseList = getMonthList(currentDate);


        String[] result = getStartAndNextMonthStartDates(formattedDate);

        System.out.println("Start of the month: " + result[0]);
        System.out.println("Start of the next month: " + result[1]);
        showExpensesByMonth(result[0], result[1], monthExpenseList);

    }

    private void showExpensesByMonth(String startDate, String endDate, List<MonthExpense> monthExpenseList) {
        ShareData shareData = new ShareData(context);
        String username = shareData.getString(ShareData.USERNAME, "");

        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance();
        expenseAPI.getExpenseByDuration(username, startDate, endDate,
                myExpensesList -> {
                    printData(myExpensesList, monthExpenseList);
                }, message -> {
                    Log.e(TAG, "showExpensesByMonth: Exception: " + message);
                });
    }

    @NonNull
    private List<MonthExpense> getMonthList(LocalDate currentDate) {
        YearMonth currentYearMonth = YearMonth.from(currentDate);
        int totalDaysInMonth = currentYearMonth.lengthOfMonth();

        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        DayOfWeek startDay = firstDayOfMonth.getDayOfWeek();
        int days = startDay.getValue();
        List<MonthExpense> list = previousMonthLastDay(days - 1);

        for (int day = 1; day <= totalDaysInMonth; day++) {
            list.add(new MonthExpense("" + day, true));
        }
        int nextMonthDay = list.size() % 7;
        if (nextMonthDay > 0)
            for (int i = 1; i <= 7 - nextMonthDay; i++) {
                list.add(new MonthExpense("" + i, false));
            }
        return list;
    }

    @NonNull
    private List<MonthExpense> previousMonthLastDay(int daysToRetrieve) {
        if (daysToRetrieve == 0) return new ArrayList<>();
        List<MonthExpense> list = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        YearMonth previousMonth = YearMonth.from(currentDate.minusMonths(1));
        LocalDate lastDayOfPreviousMonth = previousMonth.atEndOfMonth();
        for (int i = 0; i < daysToRetrieve; i++) {
            LocalDate day = lastDayOfPreviousMonth.minusDays(i);
            list.add(new MonthExpense(String.valueOf(day.getDayOfMonth()), false));
        }
        Collections.reverse(list);
        return list;
    }

    @NonNull
    public static String[] getStartAndNextMonthStartDates(String input) {
        // Parse input as "MMMM yyyy" (e.g., "May 2024")
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        LocalDate givenMonth = LocalDate.parse(input + " 01", DateTimeFormatter.ofPattern("MMMM yyyy dd", Locale.ENGLISH));

        // Get start of the given month
        LocalDate startOfGivenMonth = givenMonth.withDayOfMonth(1);

        // Get start of the next month
        LocalDate startOfNextMonth = startOfGivenMonth.plusMonths(1).withDayOfMonth(1);

        // Format the dates to "YYYY-MM-DD"
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startOfMonth = startOfGivenMonth.format(outputFormatter);
        String startOfNextMonth1 = startOfNextMonth.format(outputFormatter);

        return new String[]{startOfMonth, startOfNextMonth1};
    }


    @SuppressLint("DefaultLocale")
    private void printData(List<MyExpenses> expenses, List<MonthExpense> monthExpenseList) {
        Map<String, Double> result = calculateTotalBalance(expenses);
        System.out.println("Total Credit: " + result.get("totalCredit"));
        System.out.println("Total Debit: " + result.get("totalDebit"));
        System.out.println("Available Balance: " + result.get("availableBalance"));
        binding.tvExpense.setText(String.format("%.0f", result.get("totalDebit")));
        binding.tvIncome.setText(String.format("%.0f", result.get("totalCredit")));
        binding.tvBalance.setText(String.format("%.0f", result.get("availableBalance")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        // Calculate daily credit and debit balances
        Map<String, Map<String, Double>> dailyBalances = calculateDailyBalances(expenses);
        dailyBalances.forEach((date, balance) -> {
            System.out.println("Date: " + date);
            System.out.println("Daily Credit: " + balance.get("dailyCredit"));
            System.out.println("Daily Debit: " + balance.get("dailyDebit"));

            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

            int dayOfMonth = dateTime.getDayOfMonth();

            System.out.println("Day of the month: " + dayOfMonth);
            for (MonthExpense monthExpense : monthExpenseList) {
                if (monthExpense.getDate().equalsIgnoreCase(String.valueOf(dayOfMonth))) {
                    monthExpense.setExpense(balance.get("dailyDebit"));
                    monthExpense.setIncome(balance.get("dailyCredit"));
                }
            }
        });
        MonthViewAdapter adapter = new MonthViewAdapter(monthExpenseList);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(context, 7));
        binding.recyclerView.setAdapter(adapter);
    }

    // Method to calculate total credit, total debit, and available balance
    public static Map<String, Double> calculateTotalBalance(List<MyExpenses> expenses) {
        double totalCredit = 0;
        double totalDebit = 0;

        for (MyExpenses expense : expenses) {
            if (expense.getTransactionType().equalsIgnoreCase("CREDIT")) {
                totalCredit += expense.getExpenseAmount();
            } else if (expense.getTransactionType().equalsIgnoreCase("DEBIT")) {
                totalDebit += expense.getExpenseAmount();
            }
        }

        double availableBalance = totalCredit - totalDebit;

        Map<String, Double> balanceSummary = new HashMap<>();
        balanceSummary.put("totalCredit", totalCredit);
        balanceSummary.put("totalDebit", totalDebit);
        balanceSummary.put("availableBalance", availableBalance);

        return balanceSummary;
    }

    // Method to calculate daily credit and debit balances
    @NonNull
    public static Map<String, Map<String, Double>> calculateDailyBalances(@NonNull List<MyExpenses> expenses) {
        Map<String, List<MyExpenses>> expensesByDate = expenses.stream()
                .collect(Collectors.groupingBy(MyExpenses::getDate));

        Map<String, Map<String, Double>> dailyBalances = new HashMap<>();

        for (Map.Entry<String, List<MyExpenses>> entry : expensesByDate.entrySet()) {
            String date = entry.getKey();
            List<MyExpenses> dailyExpenses = entry.getValue();

            double dailyCredit = 0;
            double dailyDebit = 0;

            for (MyExpenses expense : dailyExpenses) {
                if (expense.getTransactionType().equalsIgnoreCase("CREDIT")) {
                    dailyCredit += expense.getExpenseAmount();
                } else if (expense.getTransactionType().equalsIgnoreCase("DEBIT")) {
                    dailyDebit += expense.getExpenseAmount();
                }
            }

            Map<String, Double> balance = new HashMap<>();
            balance.put("dailyCredit", dailyCredit);
            balance.put("dailyDebit", dailyDebit);

            dailyBalances.put(date, balance);
        }

        return dailyBalances;
    }
}