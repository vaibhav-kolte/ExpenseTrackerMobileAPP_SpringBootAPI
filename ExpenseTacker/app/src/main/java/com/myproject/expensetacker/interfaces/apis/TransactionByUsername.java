package com.myproject.expensetacker.interfaces.apis;

import com.myproject.expensetacker.model.Transaction;

import java.util.List;

public interface TransactionByUsername {
    void myTransaction(List<Transaction> transactionList);
}
