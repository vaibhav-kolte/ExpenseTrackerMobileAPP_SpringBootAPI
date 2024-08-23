package com.mycode.myExpenseTracker.service;

import com.mycode.myExpenseTracker.model.Expense;
import com.mycode.myExpenseTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {


    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense get(Integer id){
        return expenseRepository.findById(id).get();
    }

    public void save(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> getByUsername(String username) {
        return expenseRepository.findByUsername(username);
    }
    public void delete(Integer id){
        expenseRepository.deleteById(id);
    }

    public boolean checkUsernameAndIdExists(String username, Integer id) {
        return expenseRepository.existsByUsernameAndId(username, id);
    }

    public void deleteExpensesByUsername(String username) {
        expenseRepository.deleteByUsername(username);
    }

    public double getAvailableBalance(String username) {
        return expenseRepository.getAvailableBalanceByUsername(username);
    }
}
