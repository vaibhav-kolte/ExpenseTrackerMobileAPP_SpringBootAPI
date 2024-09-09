package com.myproject.expensetacker.adapter;

import static com.myproject.expensetacker.utils.Constant.USED_DATABASE;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.databinding.ShowExpenseLayoutBinding;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.ui.AddExpensesActivity;
import com.myproject.expensetacker.ui.MainActivity;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShowExpenseLayoutBinding binding = ShowExpenseLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyExpenses expenses = expensesList.get(position);

        if (expenses.getExpenseName().isEmpty()) {
            holder.binding.llExpenseLayout.setVisibility(View.GONE);
        } else {
            holder.binding.tvExpense.setText(expenses.getExpenseName());
        }

        if (String.valueOf(expenses.getExpenseAmount()).isEmpty()) {
            holder.binding.llExpenseAmountLayout.setVisibility(View.GONE);
        } else {
            holder.binding.tvExpenseAmount.setText(String.valueOf(expenses.getExpenseAmount()));
        }
        if (expenses.getDate().isEmpty()) {
            holder.binding.llExpenseDateLayout.setVisibility(View.GONE);
        } else {
            holder.binding.tvExpenseDate.setText(expenses.getFormatedDate());
        }

        if (expenses.getExpenseType().isEmpty()) {
            holder.binding.llExpenseTypeLayout.setVisibility(View.GONE);
        } else {
            holder.binding.tvExpenseType.setText(expenses.getExpenseType());
        }

        if (expenses.getTransactionType().isEmpty()) {
            holder.binding.llTransactionLayout.setVisibility(View.GONE);
        } else {
            holder.binding.tvTransaction.setText(expenses.getTransactionType());
        }

        if (expenses.getTransactionType().equalsIgnoreCase("Credit")) {
            holder.binding.imgEditExpense.setVisibility(View.GONE);
            holder.binding.imgDeleteExpense.setVisibility(View.GONE);
        }

        holder.binding.imgDeleteExpense.setOnClickListener(view -> deleteExpense(expenses, position));

        holder.binding.imgEditExpense.setOnClickListener(view -> {
            Intent i = new Intent(holder.binding.getRoot().getContext(), MainActivity.class);
            i.putExtra("EXPENSE_OBJECT", expenses);
            holder.binding.getRoot().getContext().startActivity(i);
        });
    }

    private void deleteExpense(MyExpenses expenses, int position) {
        ExpenseAPI expenseAPI = ExpenseAPIImpl.getInstance();
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShowExpenseLayoutBinding binding;

        public ViewHolder(ShowExpenseLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
