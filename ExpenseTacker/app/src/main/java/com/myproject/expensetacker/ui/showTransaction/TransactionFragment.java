package com.myproject.expensetacker.ui.showTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.myproject.expensetacker.databinding.FragmentTransactionBinding;
import com.myproject.expensetacker.utils.ShareData;


public class TransactionFragment extends Fragment {
    private FragmentTransactionBinding binding;
    private TransactionViewModel transactionViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        transactionViewModel =
                new ViewModelProvider(this).get(TransactionViewModel.class);

        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        transactionViewModel.getText().observe(getViewLifecycleOwner(), binding.tvTransaction::setText);

        getMyTransaction();
        return root;
    }

    private void getMyTransaction() {
        ShareData shareData = new ShareData(getContext());
        transactionViewModel.getTransaction(shareData.getString(ShareData.USERNAME, ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}