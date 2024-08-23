package com.myproject.expensetacker.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.databinding.ShowExpenseLayoutBinding;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.ui.AddExpensesActivity;
import com.myproject.expensetacker.utils.PrintLog;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private static final String TAG = "ExpenseAdapter";
    private final List<MyExpenses> expensesList;

    public ExpenseAdapter(List<MyExpenses> expensesList) {
        this.expensesList = expensesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem = layoutInflater.inflate(R.layout.show_expense_layout, parent, false);
//        return new ViewHolder(listItem);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShowExpenseLayoutBinding binding = ShowExpenseLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyExpenses myListData = expensesList.get(position);

        ShowExpenseLayoutBinding layoutBinding = holder.binding;
        layoutBinding.tvExpense.setText(myListData.toString());

        layoutBinding.expenseLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), "click on item: " + myListData.getId() + myListData.getExpenseName(), Toast.LENGTH_LONG).show());

        layoutBinding.imgDeleteExpense.setOnClickListener(view -> deleteExpense(myListData, position));

        layoutBinding.imgEditExpense.setOnClickListener(view -> {
            Intent i = new Intent(layoutBinding.getRoot().getContext(), AddExpensesActivity.class);
            i.putExtra("EXPENSE_OBJECT", myListData);
            layoutBinding.getRoot().getContext().startActivity(i);
        });
    }

    private void deleteExpense(MyExpenses expenses, int position) {
        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance(Database.RETROFIT);
        expenseAPI.deleteExpense(expenses.getUsername(), expenses.getId(), () -> {
            PrintLog.infoLog(TAG, "deleteExpense: Expense deleted successfully.");
            expensesList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, expensesList.size());
        }, message -> {
            PrintLog.errorLog(TAG, "deleteExpense: Exception: " + message);
        });
    }


    @Override
    public int getItemCount() {
        return expensesList.size();
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView textView;
//        public CardView expenseLayout;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            this.textView = itemView.findViewById(R.id.tv_expense);
//            this.expenseLayout = itemView.findViewById(R.id.expense_layout);
//        }
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShowExpenseLayoutBinding binding;

        public ViewHolder(ShowExpenseLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
