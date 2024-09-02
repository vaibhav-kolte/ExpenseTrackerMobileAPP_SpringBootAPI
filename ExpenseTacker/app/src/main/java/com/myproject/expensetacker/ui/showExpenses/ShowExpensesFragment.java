package com.myproject.expensetacker.ui.showExpenses;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myproject.expensetacker.adapter.ExpenseAdapter;
import com.myproject.expensetacker.databinding.FragmentShowExpensesBinding;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;


public class ShowExpensesFragment extends Fragment {
    private static final String TAG = "ShowExpensesFragment";
    private FragmentShowExpensesBinding binding;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowExpensesBinding.inflate(inflater, container, false);
        context = getContext();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyExpenses();
    }

    private void getMyExpenses() {
        ShareData shareData = new ShareData(context);
        String username = shareData.getString(ShareData.USERNAME, "");
        if (username.isEmpty()) return;

        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance(Database.RETROFIT);
        expenseAPI.getAllExpensesByUsername(username, expensesList -> {
            ExpenseAdapter adapter = new ExpenseAdapter(expensesList);
            binding.expenseRecyclerView.setHasFixedSize(true);
            binding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            binding.expenseRecyclerView.setAdapter(adapter);
        }, message -> {
            Log.e(TAG, "getExpenses: Exception: " + message);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}