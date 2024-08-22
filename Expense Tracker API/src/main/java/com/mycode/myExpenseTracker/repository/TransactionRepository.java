package com.mycode.myExpenseTracker.repository;

import com.mycode.myExpenseTracker.entities.Balance;
import com.mycode.myExpenseTracker.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUsername(String username);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType = 'CREDIT' THEN t.transactionAmount ELSE 0 END), 0) - " +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'DEBIT' THEN t.transactionAmount ELSE 0 END), 0) " +
            "FROM Transaction t WHERE t.username = :username")
    double getAvailableBalanceByUsername(@Param("username") String username);

    @Query("SELECT new com.mycode.myExpenseTracker.entities.Balance(" +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'CREDIT' THEN t.transactionAmount END), 0) - " +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'DEBIT' THEN t.transactionAmount END), 0), " +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'CREDIT' THEN t.transactionAmount END), 0), " +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'DEBIT' THEN t.transactionAmount END), 0)) " +
            "FROM Transaction t " +
            "WHERE t.username = :username AND " +
            "YEAR(t.date) = YEAR(CURRENT_DATE) AND " +
            "MONTH(t.date) = MONTH(CURRENT_DATE)")
    Balance findUserTransactionSummary(@Param("username") String username);


//    @Query("SELECT new com.mycode.myExpenseTracker.entities.MonthlyExpenseDTO(" +
//            "credit.month, credit.totalCredit, debit.totalDebit, " +
//            "(credit.totalCredit - debit.totalDebit) as totalExpense) FROM (" +
//            "SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m') AS month, " +
//            "SUM(t.transactionAmount) AS totalCredit FROM Transaction t " +
//            "WHERE t.username = :username AND t.transactionType = 'CREDIT' " +
//            "GROUP BY FUNCTION('DATE_FORMAT', t.date, '%Y-%m')) credit LEFT JOIN (" +
//            "SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m') AS month, " +
//            "SUM(t.transactionAmount) AS totalDebit FROM Transaction t " +
//            "WHERE t.username = :username AND t.transactionType = 'DEBIT' " +
//            "GROUP BY FUNCTION('DATE_FORMAT', t.date, '%Y-%m')) debit " +
//            "ON credit.month = debit.month")
//    List<MonthlyExpenseDTO> findMonthlyExpenses(@Param("username") String username);

    @Query("SELECT t FROM Transaction t WHERE t.username = :username AND t.transactionType = :transactionType")
    List<Transaction> findByUsernameAndTransactionType(@Param("username") String username, @Param("transactionType") String transactionType);

    @Transactional
    void deleteByUsername(String username);

}
