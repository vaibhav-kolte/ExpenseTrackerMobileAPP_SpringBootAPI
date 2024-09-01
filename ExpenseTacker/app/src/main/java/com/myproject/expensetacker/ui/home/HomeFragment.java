package com.myproject.expensetacker.ui.home;

import static com.myproject.expensetacker.utils.Utils.nextCurrentMonthDate;
import static com.myproject.expensetacker.utils.Utils.nextFinancialYearDate;
import static com.myproject.expensetacker.utils.Utils.startCurrentMonthDate;
import static com.myproject.expensetacker.utils.Utils.startFinancialYearDate;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.adapter.ExpenseTypeAdapter;
import com.myproject.expensetacker.databinding.FragmentHomeBinding;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.ui.AddBalanceActivity;
import com.myproject.expensetacker.ui.AddExpensesActivity;
import com.myproject.expensetacker.utils.PrintLog;
import com.myproject.expensetacker.utils.ShareData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
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
//            Intent intent = new Intent(context, AddTransactionActivity.class);
            startActivity(intent);
        });

        binding.fab.setOnClickListener(view -> startActivity(new Intent(context, AddExpensesActivity.class)));

        binding.tvMonthly.setOnClickListener(view -> {
            updateViewAppearance(binding.tvMonthly, binding.tvYearly,
                    startCurrentMonthDate().toString(),
                    nextCurrentMonthDate().toString());
        });

        binding.tvYearly.setOnClickListener(view -> {
            updateViewAppearance(binding.tvYearly, binding.tvMonthly,
                    startFinancialYearDate().toString(),
                    nextFinancialYearDate().toString());
        });
    }

    private void getTypeSummery(String startDate, String endDate) {
        ShareData shareData = new ShareData(requireContext().getApplicationContext());
        String username = shareData.getString(ShareData.USERNAME, "");

        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance(Database.RETROFIT);

        expenseAPIs.getExpenseByTypeAndDuration(username, startDate,
                endDate, typeSummeryList -> {
                    if (!typeSummeryList.isEmpty()) {
                        showRecyclerView(true);
                        ExpenseTypeAdapter adapter = new ExpenseTypeAdapter(typeSummeryList);
                        binding.typeSummeryRecycler.setHasFixedSize(true);
                        binding.typeSummeryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.typeSummeryRecycler.setAdapter(adapter);
                    } else {
                        showRecyclerView(false);
                        binding.tvRecordNotFound.setText(ContextCompat.getString(context, R.string.no_record_found));
                    }
                }, message -> PrintLog.errorLog(TAG, message));
    }

    private void showRecyclerView(boolean flag) {
        binding.typeSummeryRecycler.setVisibility(flag ? View.VISIBLE : View.GONE);
        binding.tvRecordNotFound.setVisibility(flag ? View.GONE : View.VISIBLE);
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

    private void updateViewAppearance(TextView selectedTextView, TextView unselectedTextView,
                                      String startDate, String endDate) {
        selectedTextView.setBackgroundResource(R.drawable.textview_rounded_background);
        selectedTextView.setTextColor(ContextCompat.getColor(context, R.color.white));

        unselectedTextView.setBackgroundResource(0);
        unselectedTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
        getTypeSummery(startDate, endDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.getMyBudgetUsingAPI();
        getCurrentMonthSummery();
        updateViewAppearance(binding.tvMonthly, binding.tvYearly, startCurrentMonthDate().toString(),
                nextCurrentMonthDate().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}