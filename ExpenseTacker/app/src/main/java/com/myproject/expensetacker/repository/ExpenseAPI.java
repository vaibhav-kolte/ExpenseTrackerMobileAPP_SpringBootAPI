package com.myproject.expensetacker.repository;

import com.myproject.expensetacker.exceptions.ImageNotValidException;
import com.myproject.expensetacker.interfaces.apis.APIException;
import com.myproject.expensetacker.interfaces.apis.AddExpenseInterface;
import com.myproject.expensetacker.interfaces.apis.CurrentBalance;
import com.myproject.expensetacker.interfaces.apis.DeleteExpense;
import com.myproject.expensetacker.interfaces.apis.DownloadProfilePhoto;
import com.myproject.expensetacker.interfaces.apis.ExpenseByUsername;
import com.myproject.expensetacker.interfaces.apis.ExpenseSummeryResponse;
import com.myproject.expensetacker.interfaces.apis.LoginSuccessfully;
import com.myproject.expensetacker.interfaces.apis.ProfilePhotoAdded;
import com.myproject.expensetacker.interfaces.apis.SigneInSuccessfully;
import com.myproject.expensetacker.interfaces.apis.UpdateExpense;
import com.myproject.expensetacker.interfaces.apis.TypeSummeryByDuration;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.model.MyExpenses;

import java.io.File;
import java.io.FileNotFoundException;

public interface ExpenseAPI {

    default void uploadProfilePhoto(String username, File image, ProfilePhotoAdded profilePhotoAdded,
                                    APIException exception) throws ImageNotValidException {
        System.out.println("Uploading Image.");
    }

    void availableBalance(String username, CurrentBalance balance, APIException exception);

    void loggedInAccount(String username, LoginSuccessfully loginSuccessfully, APIException exception);

    void signInAccount(Account account, SigneInSuccessfully inSuccessfully, APIException exception);

    void addExpense(MyExpenses expenses, AddExpenseInterface expenseInterface, APIException exception);

    void getAllExpensesByUsername(String username, ExpenseByUsername expense, APIException exception);

    void updateExpense(MyExpenses myExpenses, UpdateExpense updateExpense, APIException exception);

    void deleteExpense(String username, long id, DeleteExpense deleteExpense, APIException exception);

    void getExpenseSummeryInCurrentMonth(String username, ExpenseSummeryResponse expenseSummary, APIException exception);

    void downloadImage(String filename, DownloadProfilePhoto profilePhoto, APIException exception);

    void getExpenseByTypeAndDuration(String username,
                                     String startDate,
                                     String endDate, TypeSummeryByDuration typeSummery, APIException exception);

    void getExpenseByDuration(String username,
                              String startDate,
                              String endDate, ExpenseByUsername expense, APIException exception);

}
