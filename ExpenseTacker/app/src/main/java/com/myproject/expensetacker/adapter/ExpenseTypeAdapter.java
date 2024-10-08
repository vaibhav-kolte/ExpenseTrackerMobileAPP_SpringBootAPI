package com.myproject.expensetacker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.ExpenseTypeLayoutBinding;
import com.myproject.expensetacker.model.TypeSummery;
import com.myproject.expensetacker.utils.PrintLog;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTypeAdapter extends RecyclerView.Adapter<ExpenseTypeAdapter.ViewHolder> {
    private static final String TAG = "ExpenseAdapter";
    private final List<TypeSummery> typeSummeryList;
    private double sumAmount = 0;

    public ExpenseTypeAdapter(@NonNull List<TypeSummery> typeSummeryList) {
        List<TypeSummery> summeries = new ArrayList<>();

        for (TypeSummery summery : typeSummeryList) {
            sumAmount += summery.getTotalAmount();
            summeries.add(summery);
        }
        this.typeSummeryList = summeries;
    }

    @NonNull
    @Override
    public ExpenseTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ExpenseTypeLayoutBinding binding = ExpenseTypeLayoutBinding.inflate(inflater, parent, false);
        return new ExpenseTypeAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpenseTypeAdapter.ViewHolder holder, int position) {
        final TypeSummery summery = typeSummeryList.get(position);

        ExpenseTypeLayoutBinding layoutBinding = holder.binding;
        Context context = layoutBinding.getRoot().getContext();

        try {
            String iconName = formatToIconName(summery.getExpenseType());
            int resourceId = R.drawable.class.getField(iconName).getInt(null);
            layoutBinding.imageview.setImageDrawable(ContextCompat.getDrawable(context, resourceId));
            layoutBinding.name.setText(summery.getExpenseType());
            layoutBinding.amount.setText(String.valueOf(summery.getTotalAmount()));
            double percentage = (summery.getTotalAmount() / sumAmount) * 100;
            @SuppressLint("DefaultLocale")
            String percentageString = String.format("%.2f", percentage);
            layoutBinding.textPercentage.setText(percentageString + "%");
            layoutBinding.progressBar.setProgress((int) percentage);

        } catch (Exception e) {
            PrintLog.errorLog(TAG, e.getMessage());
        }


    }

    public static String formatToIconName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new Exception("File name should not be null or empty");
        }
        String lowerCaseName = name.toLowerCase();
        String formattedName = lowerCaseName.replace(" ", "_");
        return "icon_" + formattedName;
    }

    @Override
    public int getItemCount() {
        return typeSummeryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ExpenseTypeLayoutBinding binding;

        public ViewHolder(ExpenseTypeLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
