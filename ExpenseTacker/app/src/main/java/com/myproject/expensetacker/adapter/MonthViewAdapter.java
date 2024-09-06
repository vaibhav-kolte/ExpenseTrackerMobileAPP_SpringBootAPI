package com.myproject.expensetacker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.DayViewLayoutBinding;
import com.myproject.expensetacker.model.MonthExpense;

import java.util.List;

public class MonthViewAdapter extends RecyclerView.Adapter<MonthViewAdapter.ViewHolder> {

    private final List<MonthExpense> monthExpenseList;

    public MonthViewAdapter(List<MonthExpense> monthExpenseList) {
        this.monthExpenseList = monthExpenseList;
    }

    @NonNull
    @Override
    public MonthViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DayViewLayoutBinding binding = DayViewLayoutBinding.inflate(inflater, parent, false);
        return new MonthViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewAdapter.ViewHolder holder, int position) {
        MonthExpense monthExpense = monthExpenseList.get(position);
        holder.binding.tvDay.setText(monthExpense.getDate());
        if (monthExpense.isCurrentMonthDay()) {
            holder.binding.tvDay.setTextColor(ContextCompat.getColor(
                    holder.binding.getRoot().getContext(),
                    R.color.black
            ));
        } else {
            holder.binding.tvIncome.setVisibility(View.GONE);
            holder.binding.tvExpense.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return monthExpenseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public DayViewLayoutBinding binding;

        public ViewHolder(@NonNull DayViewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
