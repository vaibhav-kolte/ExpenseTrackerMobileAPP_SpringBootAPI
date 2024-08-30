package com.mycode.myExpenseTracker.repository;

import com.mycode.myExpenseTracker.entities.ExpenseTypeSummery;
import com.mycode.myExpenseTracker.model.LoginAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface LoginAccountRepository extends JpaRepository<LoginAccount, String> {

    LoginAccount findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LoginAccount l " +
            "WHERE l.username = :username and l.myPassword = :password")
    boolean existUser(String username, String password);

    @Query("SELECT la FROM LoginAccount la WHERE la.name = :fileName")
    Optional<LoginAccount> findByName(String fileName);

}
