package com.myproject.expensetacker.repository.room;

import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.DeleteExpense;
import com.myproject.expensetacker.interfaces.apis.ExpenseByUsername;
import com.myproject.expensetacker.interfaces.apis.ExpenseSummeryResponse;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.MyLogin;
import com.myproject.expensetacker.interfaces.apis.ProfilePhotoAdded;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.interfaces.apis.UpdateExpense;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.ExpenseSummary;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;

import java.io.File;

public class RoomManager implements ExpenseAPI {
    @Override
    public void uploadProfilePhoto(String username, File image, ProfilePhotoAdded profilePhotoAdded, APIException exception) {

    }

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
    public void getMyAccount(String username, MyLogin myLogin, APIException exception) {

    }

    @Override
    public void getExpenseSummeryInCurrentMonth(String username, ExpenseSummeryResponse expenseSummary, APIException exception) {

    }
}
