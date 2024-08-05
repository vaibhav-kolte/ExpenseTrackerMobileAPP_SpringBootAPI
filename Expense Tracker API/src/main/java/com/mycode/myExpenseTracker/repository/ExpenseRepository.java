package com.mycode.myExpenseTracker.repository;

import com.mycode.myExpenseTracker.model.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findByUsername(String username);

    boolean existsByUsernameAndId(String username, Integer id);

    @Transactional
    void deleteByUsername(String username);
}
