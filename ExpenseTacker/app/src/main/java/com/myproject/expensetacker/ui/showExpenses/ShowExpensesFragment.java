package com.myproject.expensetacker.ui.showExpenses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.expensetacker.databinding.FragmentShowExpensesBinding;
import com.myproject.expensetacker.utils.ShareData;


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

        final TextView tvShowExpense = binding.tvShowExpense;
        showExpensesViewModel.getText().observe(getViewLifecycleOwner(), tvShowExpense::setText);

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