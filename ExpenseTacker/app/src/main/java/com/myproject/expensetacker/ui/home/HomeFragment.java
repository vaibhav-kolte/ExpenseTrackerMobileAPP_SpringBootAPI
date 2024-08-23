package com.myproject.expensetacker.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.expensetacker.databinding.FragmentHomeBinding;
import com.myproject.expensetacker.ui.AddBalanceActivity;
import com.myproject.expensetacker.ui.AddExpensesActivity;

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

    private double getFormatedAmount(double amount){
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

    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.getMyBudgetUsingAPI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}