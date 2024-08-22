package com.myproject.expensetacker.ui.showExpenses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.expensetacker.adapter.ExpenseAdapter;
import com.myproject.expensetacker.databinding.FragmentShowExpensesBinding;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.utils.ShareData;

import java.util.List;


public class ShowExpensesFragment extends Fragment {
    private FragmentShowExpensesBinding binding;
    private ShowExpensesViewModel showExpensesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showExpensesViewModel =
                new ViewModelProvider(this).get(ShowExpensesViewModel.class);

        binding = FragmentShowExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        showExpensesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<MyExpenses>>() {
            @Override
            public void onChanged(List<MyExpenses> myExpenses) {
                ExpenseAdapter adapter = new ExpenseAdapter(myExpenses);
                binding.expenseRecyclerView.setHasFixedSize(true);
                binding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.expenseRecyclerView.setAdapter(adapter);
            }
        });

        getMyExpenses();
        return root;
    }

    private void getMyExpenses() {
        ShareData shareData = new ShareData(getContext());
        showExpensesViewModel.getExpenses(shareData.getString(ShareData.USERNAME, ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}