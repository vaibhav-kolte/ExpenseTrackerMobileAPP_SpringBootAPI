package com.mycode.myExpenseTracker.repository;

import com.mycode.myExpenseTracker.entities.DailyTransactionSummary;
import com.mycode.myExpenseTracker.entities.ExpenseSummary;
import com.mycode.myExpenseTracker.entities.ExpenseTypeSummery;
import com.mycode.myExpenseTracker.model.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

//    @Query(value = "SELECT e.date AS transactionDate, " +
//            "COALESCE(SUM(CASE WHEN e.transaction_type = 'CREDIT' THEN e.expense_amount ELSE 0 END), 0) AS totalIncome, " +
//            "COALESCE(SUM(CASE WHEN e.transaction_type = 'DEBIT' THEN e.expense_amount ELSE 0 END), 0) AS totalExpenses " +
//            "FROM expense e WHERE e.username = :username AND e.date BETWEEN :startDate AND :endDate " +
//            "GROUP BY e.date ORDER BY e.date", nativeQuery = true)
//    List<DailyTransactionSummary> findExpenseSummary(@Param("username") String username,
//                                                     @Param("startDate") LocalDateTime startDate,
//                                                     @Param("endDate") LocalDateTime endDate);


//    @Query("SELECT new com.mycode.myExpenseTracker.entities.DailyTransactionSummary(e.date, " +
//            "COALESCE(SUM(CASE WHEN e.transactionType = 'CREDIT' THEN e.expenseAmount ELSE 0 END), 0), " +
//            "COALESCE(SUM(CASE WHEN e.transactionType = 'DEBIT' THEN e.expenseAmount ELSE 0 END), 0)) " +
//            "FROM Expense e WHERE e.username = :username AND e.date BETWEEN :startDate AND :endDate " +
//            "GROUP BY e.date ORDER BY e.date")
//    List<DailyTransactionSummary> findExpenseSummary(@Param("username") String username,
//                                                     @Param("startDate") LocalDateTime startDate,
//                                                     @Param("endDate") LocalDateTime endDate);
//    Reason: Validation failed for query for method public abstract java.util.List com.mycode.myExpenseTracker.repository.ExpenseRepository.findExpenseSummary(java.lang.String,java.time.LocalDateTime,java.time.LocalDateTime)

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

//    @Query("SELECT new com.mycode.myExpenseTracker.entities.DailyTransactionSummary(e.date, " +
//            "SUM(CASE WHEN e.transactionType = 'CREDIT' THEN e.expenseAmount ELSE 0 END), " +
//            "SUM(CASE WHEN e.transactionType = 'DEBIT' THEN e.expenseAmount ELSE 0 END))" +
//            "FROM Expense e " +
//            "WHERE e.username = :username AND e.date BETWEEN :startDate AND :endDate " +
//            "GROUP BY e.date " +
//            "ORDER BY e.date")
//    List<DailyTransactionSummary> getDailyIncomeAndExpenses(@Param("username") String username,
//                                                           LocalDateTime startDate,
//                                                           LocalDateTime endDate);

//    @Query(value = "SELECT " +
//            "date AS transaction_date, " +
//            "SUM(CASE WHEN transaction_type = 'CREDIT' THEN expense_amount ELSE 0 END) AS total_credit, " +
//            "SUM(CASE WHEN transaction_type = 'DEBIT' THEN expense_amount ELSE 0 END) AS total_debit " +
//            "FROM Expense " +
//            "WHERE username = :username AND date BETWEEN :startDate AND :endDate " +
//            "GROUP BY date", nativeQuery = true)


}
