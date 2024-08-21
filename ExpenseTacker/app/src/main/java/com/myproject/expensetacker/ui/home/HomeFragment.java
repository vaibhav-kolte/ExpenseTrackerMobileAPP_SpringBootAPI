package com.myproject.expensetacker.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.expensetacker.databinding.FragmentHomeBinding;
import com.myproject.expensetacker.repository.ExpenseAPIs;
import com.myproject.expensetacker.repository.retrofit.RetrofitManager;
import com.myproject.expensetacker.ui.AddBalanceActivity;

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

        homeViewModel.getText().observe(getViewLifecycleOwner(), binding.textHome::setText);
        homeViewModel.getMyBudget().observe(getViewLifecycleOwner(),
                aDouble -> binding.tvMyBudget.setText(String.valueOf(aDouble)));

        context = getContext();
        handleOnClickEvents();
        return root;
    }


    private void handleOnClickEvents() {
        binding.bankCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddBalanceActivity.class);
            startActivity(intent);
        });

        binding.btnCheckBalance.setOnClickListener(view -> {
            ExpenseAPIs expenseAPIs = new RetrofitManager();
            expenseAPIs.availableBalance("pankaj", balance -> {
                Toast.makeText(context, "Got balance: " + balance, Toast.LENGTH_SHORT).show();
            }, message -> {
                Toast.makeText(context, "Failed to get balance: " + message, Toast.LENGTH_SHORT).show();
            });
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