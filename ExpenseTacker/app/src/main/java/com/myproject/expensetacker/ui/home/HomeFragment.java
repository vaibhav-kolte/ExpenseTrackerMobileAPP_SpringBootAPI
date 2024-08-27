package com.myproject.expensetacker.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.FragmentHomeBinding;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.ui.AddBalanceActivity;
import com.myproject.expensetacker.ui.AddExpensesActivity;
import com.myproject.expensetacker.ui.fragments.MonthlyFragment;
import com.myproject.expensetacker.ui.fragments.YearlyFragment;
import com.myproject.expensetacker.utils.ShareData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Context context;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getMyBudget().observe(getViewLifecycleOwner(),
                aDouble -> binding.tvMyBudget.setText(String.valueOf(getFormatedAmount(aDouble))));

        homeViewModel.getBalanceSummery().observe(getViewLifecycleOwner(), balanceSummery -> {
            binding.tvIncomeBalance.setText(String.valueOf(balanceSummery.getCreditCurrentMonth()));
            binding.tvExpenseBalance.setText(String.valueOf(balanceSummery.getDebitCurrentMonth()));
        });

        context = getContext();
        handleOnClickEvents();
        return root;
    }

    private double getFormatedAmount(double amount) {
        BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void handleOnClickEvents() {
        binding.bankCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddBalanceActivity.class);
            startActivity(intent);
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(context, AddExpensesActivity.class));
        });

        binding.tvMonthly.setOnClickListener(view -> {
            updateViewAppearance(binding.tvMonthly, binding.tvYearly, new MonthlyFragment());
        });

        binding.tvYearly.setOnClickListener(view -> {
            updateViewAppearance(binding.tvYearly, binding.tvMonthly, new YearlyFragment());
        });
    }

    private void updateViewAppearance(TextView selectedTextView, TextView unselectedTextView, Fragment newFragment) {
        selectedTextView.setBackgroundResource(R.drawable.textview_rounded_background);
        selectedTextView.setTextColor(ContextCompat.getColor(context, R.color.white));

        unselectedTextView.setBackgroundResource(0);
        unselectedTextView.setTextColor(ContextCompat.getColor(context, R.color.black));

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.category_fragment, newFragment)
                .commit();
    }

    private void getCurrentMonthSummery() {
        ShareData shareData = new ShareData(context.getApplicationContext());
        String username = shareData.getString(ShareData.USERNAME, "");

        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance(Database.RETROFIT);
        expenseAPIs.getExpenseSummeryInCurrentMonth(username, expenseSummary -> {
            System.out.println("Expense summery: " + expenseSummary);
            binding.tvIncomeBalance.setText(String.valueOf(expenseSummary.getTotalCredit()));
            binding.tvExpenseBalance.setText(String.valueOf(expenseSummary.getTotalDebit()));
            binding.tvCurrentMonth.setText(expenseSummary.getMonthYear());
        }, message -> {

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.getMyBudgetUsingAPI();
        getCurrentMonthSummery();
        updateViewAppearance(binding.tvMonthly, binding.tvYearly, new MonthlyFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}