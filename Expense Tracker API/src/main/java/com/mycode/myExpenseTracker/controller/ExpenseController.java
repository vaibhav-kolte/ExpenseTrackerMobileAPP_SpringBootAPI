package com.mycode.myExpenseTracker.controller;

import com.mycode.myExpenseTracker.model.Expense;
import com.mycode.myExpenseTracker.model.Transaction;
import com.mycode.myExpenseTracker.model.ErrorResponse;
import com.mycode.myExpenseTracker.service.ExpenseService;
import com.mycode.myExpenseTracker.service.LoginAccountService;
import com.mycode.myExpenseTracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private LoginAccountService loginAccountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Expense expense) {
        try {
            if (expense.getUsername().isEmpty()
                    || String.valueOf(expense.getExpenseAmount()).isEmpty()
                    || String.valueOf(expense.getDate()).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (loginAccountService.existsByUsername(expense.getUsername())) {
                if(expense.getTransactionType().equalsIgnoreCase("credit")){
                    expenseService.save(expense);
                    return new ResponseEntity<>(HttpStatus.OK);
                }

                if (expenseService.getAvailableBalance(expense.getUsername())
                        >= expense.getExpenseAmount()) {
                    expenseService.save(expense);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        "Insufficient balance in your account"), HttpStatus.BAD_REQUEST);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll/{username}")
    public ResponseEntity<List<Expense>> getByUsername(@PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                List<Expense> expense = expenseService.getByUsername(username);
                return new ResponseEntity<>(expense, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            System.out.println("Not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{username}/{id}")
    public ResponseEntity<Expense> delete(@PathVariable String username,
                                          @PathVariable int id) {
        if (expenseService.checkUsernameAndIdExists(username, id)) {
            Expense expense = expenseService.get(id);
            transactionService.save(new Transaction(
                    expense.getUsername(),
                    expense.getDate(),
                    expense.getExpenseAmount(),
                    "CREDIT"
            ));
            expenseService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{username}/{id}")
    public ResponseEntity<?> update(@RequestBody Expense expense,
                                    @PathVariable String username,
                                    @PathVariable Integer id) {
        try {
            if (expenseService.checkUsernameAndIdExists(username, id)) {
                Expense oldExpense = expenseService.get(id);
                if (oldExpense.getExpenseAmount() > expense.getExpenseAmount()) {
                    transactionService.save(new Transaction(
                            expense.getUsername(),
                            expense.getDate(),
                            oldExpense.getExpenseAmount() - expense.getExpenseAmount(),
                            "CREDIT"
                    ));
                }

                if (oldExpense.getExpenseAmount() < expense.getExpenseAmount()) {
                    double availableBalance = transactionService.getAvailableBalance(username);
                    double expenseAmount = expense.getExpenseAmount() - oldExpense.getExpenseAmount();
                    if (expenseAmount <= availableBalance) {
                        transactionService.save(new Transaction(
                                expense.getUsername(),
                                expense.getDate(),
                                expenseAmount,
                                "DEBIT"
                        ));
                    } else {
                        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                "Insufficient balance in your account"), HttpStatus.BAD_REQUEST);
                    }

                }
                expenseService.save(expense);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-expense/{username}")
    public ResponseEntity<?> deleteExpenseByUsername(@PathVariable String username) {
        try {
            expenseService.deleteExpensesByUsername(username);
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "Expenses deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/available-balance/{username}")
    public ResponseEntity<?> getAvailableBalance(@PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                double availableBalance = expenseService.getAvailableBalance(username);
                return new ResponseEntity<>(availableBalance, HttpStatus.OK);
            } else {
                ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        "User is not exits");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
