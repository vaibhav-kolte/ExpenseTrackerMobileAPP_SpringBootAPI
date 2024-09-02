package com.myproject.expensetacker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.MonthlyViewLayoutBinding;
import com.myproject.expensetacker.interfaces.SelectedMonth;
import com.myproject.expensetacker.model.MonthlyView;

import java.util.List;

public class MonthlyViewAdapter extends RecyclerView.Adapter<MonthlyViewAdapter.ViewHolder> {

    private final List<MonthlyView> viewList;
    private ConstraintLayout previousLayout;
    private final SelectedMonth selectedMonth;

    public MonthlyViewAdapter(List<MonthlyView> viewList, SelectedMonth selectedMonth) {
        this.viewList = viewList;
        this.selectedMonth = selectedMonth;
    }

    @NonNull
    @Override
    public MonthlyViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MonthlyViewLayoutBinding binding = MonthlyViewLayoutBinding.inflate(inflater, parent, false);
        return new MonthlyViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyViewAdapter.ViewHolder holder, int position) {
        MonthlyView monthlyView = viewList.get(position);

        holder.binding.tvMonth.setText(monthlyView.getMonthName());

        holder.binding.monthlyViewLayout.setOnClickListener(view -> {
            updateResource(holder.binding.monthlyViewLayout, monthlyView);
        });

        if (monthlyView.getMonthNumber() == -1) {
            updateResource(holder.binding.monthlyViewLayout, monthlyView);
        }
    }

    private void updateResource(@NonNull ConstraintLayout monthlyViewLayout, MonthlyView monthlyView) {
        monthlyViewLayout.setBackgroundColor(ContextCompat.getColor(monthlyViewLayout.getContext(),
                R.color.white));
        if (previousLayout != null) {
            previousLayout.setBackgroundColor(ContextCompat.getColor(monthlyViewLayout.getContext(),
                    R.color.monthly_view_color));
        }
        previousLayout = monthlyViewLayout;
        selectedMonth.selectedMonth(monthlyView);
    }


    @Override
    public int getItemCount() {
        return viewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MonthlyViewLayoutBinding binding;

        public ViewHolder(@NonNull MonthlyViewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
