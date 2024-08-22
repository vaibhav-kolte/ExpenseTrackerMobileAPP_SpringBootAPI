package com.mycode.myExpenseTracker.service;


import com.mycode.myExpenseTracker.entities.Balance;
import com.mycode.myExpenseTracker.entities.MonthlyExpenseDTO;
import com.mycode.myExpenseTracker.model.Transaction;
import com.mycode.myExpenseTracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getByUsername(String username) {
        return transactionRepository.findByUsername(username);
    }

    public double getAvailableBalance(String username) {
        return transactionRepository.getAvailableBalanceByUsername(username);
    }

    public Balance getBalance(String username) {
        return transactionRepository.findUserTransactionSummary(username);
    }

//    public List<MonthlyExpenseDTO> findMonthlyExpenses(String username) {
//        return transactionRepository.findMonthlyExpenses(username);
//    }


    public List<Transaction> getTransactionsForUser(String transactionType,String username) {
        return transactionRepository.findByUsernameAndTransactionType(username, transactionType);
    }

    public void deleteTransactionByUsername(String username) {
        transactionRepository.deleteByUsername(username);
    }

}
