package com.mycode.myExpenseTracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String expenseName;

    @Column(nullable = false)
    private double expenseAmount;

    @Column(nullable = false)
    private Date date;

    private String expenseType;

    @Column(nullable = false)
    private String transactionType;

    public Expense() {
    }

    @Override
    public String toString() {
        return "AddExpenseController{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", expenseName='" + expenseName + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", date=" + date +
                ", expenseType='" + expenseType + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
