package com.mycode.myExpenseTracker.repository;

import com.mycode.myExpenseTracker.model.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findByUsername(String username);

    boolean existsByUsernameAndId(String username, Integer id);

    @Transactional
    void deleteByUsername(String username);

    @Query("SELECT COALESCE(SUM(CASE WHEN e.transactionType = 'CREDIT' THEN e.expenseAmount ELSE 0 END), 0) - " +
            "COALESCE(SUM(CASE WHEN e.transactionType = 'DEBIT' THEN e.expenseAmount ELSE 0 END), 0) " +
            "FROM Expense e WHERE e.username = :username")
    double getAvailableBalanceByUsername(@Param("username") String username);
}
