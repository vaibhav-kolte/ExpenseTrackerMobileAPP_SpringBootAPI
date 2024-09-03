package com.mycode.myExpenseTracker.repository;

import com.mycode.myExpenseTracker.entities.ExpenseSummary;
import com.mycode.myExpenseTracker.entities.ExpenseTypeSummery;
import com.mycode.myExpenseTracker.model.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("SELECT e FROM Expense e WHERE e.username = :username AND e.expenseName != \"\" ORDER BY e.id DESC")
    List<Expense> findByUsername(@Param("username") String username);

    boolean existsByUsernameAndId(String username, Integer id);

    @Transactional
    void deleteByUsername(String username);

    @Query("SELECT COALESCE(SUM(CASE WHEN e.transactionType = 'CREDIT' THEN e.expenseAmount ELSE 0 END), 0) - " +
            "COALESCE(SUM(CASE WHEN e.transactionType = 'DEBIT' THEN e.expenseAmount ELSE 0 END), 0) " +
            "FROM Expense e WHERE e.username = :username")
    double getAvailableBalanceByUsername(@Param("username") String username);

    @Query("SELECT " +
            "SUM(CASE WHEN e.transactionType = 'DEBIT' THEN e.expenseAmount ELSE 0 END) AS totalDebit, " +
            "SUM(CASE WHEN e.transactionType = 'CREDIT' THEN e.expenseAmount ELSE 0 END) AS totalCredit, " +
            "CONCAT(FUNCTION('MONTHNAME', CURRENT_DATE), ' ', YEAR(CURRENT_DATE)) AS monthYear " +
            "FROM Expense e WHERE e.username = :username AND MONTH(e.date) = MONTH(CURRENT_DATE) AND YEAR(e.date) = YEAR(CURRENT_DATE)")
    ExpenseSummary findCurrentMonthSummeryByUsername(@Param("username") String username);

    @Query("SELECT new com.mycode.myExpenseTracker.entities.ExpenseTypeSummery(e.expenseType, " +
            "SUM(e.expenseAmount)) FROM Expense e " +
            "WHERE e.username = :username GROUP BY e.expenseType")
    List<ExpenseTypeSummery> findMonthlyExpenseByType(@Param("username") String username);

    @Query("SELECT new com.mycode.myExpenseTracker.entities.ExpenseTypeSummery(e.expenseType, " +
            "SUM(e.expenseAmount)) FROM Expense e WHERE e.username = :username " +
            "AND e.date BETWEEN :startDate AND :endDate AND e.expenseType != \"\"" +
            "GROUP BY e.expenseType")
    List<ExpenseTypeSummery> findExpenseByType(@Param("username") String username,
                                               LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM Expense e WHERE e.username = :username " +
            "AND e.date BETWEEN :startDate AND :endDate ORDER BY e.id DESC")
    List<Expense> getExpenseByDuration(@Param("username") String username,
                                       LocalDateTime startDate, LocalDateTime endDate);
}
