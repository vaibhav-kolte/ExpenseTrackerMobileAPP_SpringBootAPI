package com.mycode.myExpenseTracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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
    private String date;

    private String expenseType;

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
                '}';
    }
}
