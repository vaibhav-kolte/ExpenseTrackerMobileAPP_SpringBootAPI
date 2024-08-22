package com.myproject.expensetacker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.model.MyExpenses;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private final List<MyExpenses> expensesList;

    public ExpenseAdapter(List<MyExpenses> expensesList) {
        this.expensesList = expensesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.show_expense_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyExpenses myListData = expensesList.get(position);
        holder.textView.setText(myListData.toString());

        holder.expenseLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), "click on item: " + myListData.getId() + myListData.getExpenseName(), Toast.LENGTH_LONG).show());
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CardView expenseLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_expense);
            this.expenseLayout = itemView.findViewById(R.id.expense_layout);
        }
    }
}
