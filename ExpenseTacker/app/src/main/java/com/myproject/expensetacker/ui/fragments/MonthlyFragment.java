package com.myproject.expensetacker.ui.fragments;

import static com.myproject.expensetacker.utils.Utils.nextCurrentMonthDate;
import static com.myproject.expensetacker.utils.Utils.startCurrentMonthDate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myproject.expensetacker.databinding.FragmentMonthlyBinding;
import com.myproject.expensetacker.model.TypeSummery;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.PrintLog;
import com.myproject.expensetacker.utils.ShareData;

public class MonthlyFragment extends Fragment {
    private static final String TAG = "MonthlyFragment";
    private FragmentMonthlyBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMonthlyBinding.inflate(inflater, container, false);
        handleOnClickEvents();
        return binding.getRoot();
    }

    private void handleOnClickEvents() {

    }


    @Override
    public void onResume() {
        super.onResume();
        getMonthlyTypeSummery();
    }

    private void getMonthlyTypeSummery() {
        ShareData shareData = new ShareData(requireContext().getApplicationContext());
        String username = shareData.getString(ShareData.USERNAME, "");

        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance(Database.RETROFIT);

        expenseAPIs.getExpenseByTypeAndDuration(username, startCurrentMonthDate().toString(),
                nextCurrentMonthDate().toString(), typeSummeryList -> {
                    StringBuffer stringBuffer = new StringBuffer();

                    for (TypeSummery expenseTypeSummery : typeSummeryList) {
                        if (expenseTypeSummery.getExpenseType().isEmpty()) continue;
                        stringBuffer.append(expenseTypeSummery);
                        stringBuffer.append("\n\n");
                    }
                    binding.typeSummery.setText(stringBuffer);
                }, message -> PrintLog.errorLog(TAG, message));
    }
}