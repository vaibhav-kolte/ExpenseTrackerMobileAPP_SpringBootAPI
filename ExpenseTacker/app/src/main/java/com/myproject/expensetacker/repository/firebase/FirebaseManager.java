package com.myproject.expensetacker.repository.firebase;

import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.DeleteExpense;
import com.myproject.expensetacker.interfaces.apis.DownloadProfilePhoto;
import com.myproject.expensetacker.interfaces.apis.ExpenseByUsername;
import com.myproject.expensetacker.interfaces.apis.ExpenseSummeryResponse;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.interfaces.apis.UpdateExpense;
import com.myproject.expensetacker.interfaces.apis.TypeSummeryByDuration;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;


public class FirebaseManager implements ExpenseAPI {

    @Override
    public void availableBalance(String username, CurrentBalance balance, APIException exception) {

    }

    @Override
    public void loggedInAccount(String username, LoginSuccessfully loginSuccessfully, APIException exception) {

    }

    @Override
    public void signInAccount(Account account, SigneInSuccessfully inSuccessfully, APIException exception) {

    }


    @Override
    public void addExpense(MyExpenses expenses, AddExpenseInterface expenseInterface, APIException exception) {

    }


    @Override
    public void getAllExpensesByUsername(String username, ExpenseByUsername expense, APIException exception) {

    }

    @Override
    public void updateExpense(MyExpenses myExpenses, UpdateExpense updateExpense, APIException exception) {

    }

    @Override
    public void deleteExpense(String username, long id, DeleteExpense deleteExpense, APIException exception) {

    }

    @Override
    public void getExpenseSummeryInCurrentMonth(String username, ExpenseSummeryResponse expenseSummary, APIException exception) {

    }

    @Override
    public void downloadImage(String filename, DownloadProfilePhoto profilePhoto, APIException exception) {

    }

    @Override
    public void getExpenseByTypeAndDuration(String username, String startDate, String endDate, TypeSummeryByDuration typeSummery, APIException exception) {

    }

    @Override
    public void getExpenseByDuration(String username, String startDate, String endDate, ExpenseByUsername expense, APIException exception) {

    }
}