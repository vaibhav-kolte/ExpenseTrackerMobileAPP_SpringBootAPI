package com.myproject.expensetacker.repository;

import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddBalanceInterface;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.AddBalance;
import com.myproject.expensetacker.model.MyExpenses;

public interface ExpenseAPIs {
    void availableBalance(String username, CurrentBalance balance, APIException exception);

    void loggedInAccount(String username, LoginSuccessfully loginSuccessfully, APIException exception);

    void signInAccount(Account account, SigneInSuccessfully inSuccessfully, APIException exception);

    void addBalance(AddBalance balance, AddBalanceInterface balanceInterface, APIException exception);

    void addExpense(MyExpenses expenses, AddExpenseInterface expenseInterface, APIException exception);
}
