package com.myproject.expensetacker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.myproject.expensetacker.R;
import com.myproject.expensetacker.adapter.MonthlyViewAdapter;
import com.myproject.expensetacker.databinding.ActivityShowExpensesBinding;
import com.myproject.expensetacker.interfaces.SelectedMonth;
import com.myproject.expensetacker.model.MonthlyView;
import com.myproject.expensetacker.ui.showExpenses.ShowExpensesFragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowExpensesActivity extends AppCompatActivity implements SelectedMonth {
    private static final String TAG = "ShowExpensesActivity";
    private ActivityShowExpensesBinding binding;
    private Context context;
    private List<MonthlyView> monthlyViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ShowExpensesActivity.this;
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getMyExpenses();

        handleOnCLick();

        showAllExpense();
    }

    private void showAllExpense() {
        monthlyViews = new ArrayList<>();
        monthlyViews.add(new MonthlyView(-1, "All", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(1, "Jan", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(2, "Feb", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(3, "Mar", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(4, "April", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(5, "May", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(6, "Jun", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(7, "July", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(8, "Aug", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(9, "Sept", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(10, "Oct", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(11, "Nov", R.drawable.icon_calendar_month));
        monthlyViews.add(new MonthlyView(12, "Dec", R.drawable.icon_calendar_month));

        MonthlyViewAdapter adapter = new MonthlyViewAdapter(monthlyViews, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.setAdapter(adapter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flFragment, new ShowExpensesFragment(-1))
                .commit();
    }

    private void handleOnCLick() {


    }

    @Override
    public void selectedMonth(MonthlyView monthlyView) {
        Toast.makeText(context, monthlyView.getMonthName(), Toast.LENGTH_SHORT).show();
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

//    private void getMyExpenses() {
//        ShareData shareData = new ShareData(context);
//        String username = shareData.getString(ShareData.USERNAME, "");
//        if (username.isEmpty()) return;
//
//        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance(Database.RETROFIT);
//        expenseAPI.getAllExpensesByUsername(username, expensesList -> {
//            ExpenseAdapter adapter = new ExpenseAdapter(expensesList);
//            binding.expenseRecyclerView.setHasFixedSize(true);
//            binding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//            binding.expenseRecyclerView.setAdapter(adapter);
//        }, message -> {
//            PrintLog.errorLog(TAG, "getExpenses: Exception: " + message);
//        });
//    }


}