package com.myproject.expensetacker.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.GridSpacingItemDecoration;
import com.myproject.expensetacker.adapter.MonthViewAdapter;
import com.myproject.expensetacker.databinding.ActivityMonthBinding;
import com.myproject.expensetacker.model.MonthExpense;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonthActivity extends AppCompatActivity {

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
        MonthViewAdapter adapter = new MonthViewAdapter(getMonthList(currentDate));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(context, 7));
        binding.recyclerView.setAdapter(adapter);

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
}