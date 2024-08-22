package com.myproject.expensetacker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<Transaction> transactionsList;

    public TransactionAdapter(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.show_transaction_layout, parent, false);
        return new TransactionAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        final Transaction myListData = transactionsList.get(position);
        holder.textView.setText(myListData.toString());

        holder.transactionLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), "click on item: "
                + myListData.getId() + " " + myListData.getTransactionAmount(), Toast.LENGTH_LONG).show());
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CardView transactionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_transaction);
            this.transactionLayout = itemView.findViewById(R.id.expense_layout);
        }
    }
}