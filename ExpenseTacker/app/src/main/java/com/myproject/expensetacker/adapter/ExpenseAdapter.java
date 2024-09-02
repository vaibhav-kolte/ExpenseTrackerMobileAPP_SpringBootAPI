package com.myproject.expensetacker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.databinding.ShowExpenseLayoutBinding;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
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
        final MyExpenses expenses = expensesList.get(position);
        System.out.println(expenses + "\n\n");

        ShowExpenseLayoutBinding layoutBinding = holder.binding;
        layoutBinding.expenseLayout.setOnClickListener(view -> {
            PrintLog.debugLog(TAG, expenses.toString());
        });

        System.out.println("ExpenseName: " + expenses.getExpenseName());
        if (expenses.getExpenseName().isEmpty()) {
            layoutBinding.llExpenseLayout.setVisibility(View.GONE);
            System.out.println("true");
        } else {
            layoutBinding.tvExpense.setText(expenses.getExpenseName());
            System.out.println("false");
        }

        System.out.println("Expense Amount: " + expenses.getExpenseAmount());
        if (String.valueOf(expenses.getExpenseAmount()).isEmpty()) {
            layoutBinding.llExpenseAmountLayout.setVisibility(View.GONE);
            System.out.println("true");
        } else {
            layoutBinding.tvExpenseAmount.setText(String.valueOf(expenses.getExpenseAmount()));
            System.out.println("false");
        }
        System.out.println("Expense Date: " + expenses.getDate());
        if (expenses.getDate().isEmpty()) {
            layoutBinding.llExpenseDateLayout.setVisibility(View.GONE);
            System.out.println("true");
        } else {
            layoutBinding.tvExpenseDate.setText(expenses.getFormatedDate());
            System.out.println("false");
        }

        System.out.println("Expense Type: " + expenses.getExpenseType());
        if (expenses.getExpenseType().isEmpty()) {
            layoutBinding.llExpenseTypeLayout.setVisibility(View.GONE);
            System.out.println("true");
        } else {
            layoutBinding.tvExpenseType.setText(expenses.getExpenseType());
            System.out.println("false");
        }

        System.out.println("Transaction Type: " + expenses.getTransactionType());
        if (expenses.getTransactionType().isEmpty()) {
            layoutBinding.llTransactionLayout.setVisibility(View.GONE);
            System.out.println("true");
        } else {
            layoutBinding.tvTransaction.setText(expenses.getTransactionType());
            System.out.println("false");
        }

//        if (expenses.getTransactionType().equalsIgnoreCase("Credit")) {
//            layoutBinding.imgEditExpense.setVisibility(View.GONE);
//            layoutBinding.imgDeleteExpense.setVisibility(View.GONE);
//        }

//        layoutBinding.imgDeleteExpense.setOnClickListener(view -> deleteExpense(expenses, position));
//
//        layoutBinding.imgEditExpense.setOnClickListener(view -> {
//            Intent i = new Intent(layoutBinding.getRoot().getContext(), AddExpensesActivity.class);
//            i.putExtra("EXPENSE_OBJECT", expenses);
//            layoutBinding.getRoot().getContext().startActivity(i);
//        });
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShowExpenseLayoutBinding binding;

        public ViewHolder(ShowExpenseLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
