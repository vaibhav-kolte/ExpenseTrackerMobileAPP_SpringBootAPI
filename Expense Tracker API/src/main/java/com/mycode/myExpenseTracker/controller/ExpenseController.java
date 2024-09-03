package com.mycode.myExpenseTracker.controller;

import com.mycode.myExpenseTracker.entities.ExpenseSummary;
import com.mycode.myExpenseTracker.entities.ExpenseTypeSummery;
import com.mycode.myExpenseTracker.exceptions.InsufficientBalanceException;
import com.mycode.myExpenseTracker.exceptions.InternalServerException;
import com.mycode.myExpenseTracker.exceptions.UserNotFound;
import com.mycode.myExpenseTracker.model.Expense;
import com.mycode.myExpenseTracker.service.ExpenseService;
import com.mycode.myExpenseTracker.service.LoginAccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.mycode.myExpenseTracker.utils.Util.getLocalDateTime;


@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private LoginAccountService loginAccountService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@NotNull @RequestBody Expense expense) throws InsufficientBalanceException {
        try {
            if (expense.getUsername().isEmpty()
                    || String.valueOf(expense.getExpenseAmount()).isEmpty()
                    || String.valueOf(expense.getDate()).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (loginAccountService.existsByUsername(expense.getUsername())) {
                if (expense.getTransactionType().equalsIgnoreCase("credit")) {
                    expenseService.save(expense);
                    return new ResponseEntity<>(HttpStatus.OK);
                }

                if (expenseService.getAvailableBalance(expense.getUsername())
                        >= expense.getExpenseAmount()) {
                    expenseService.save(expense);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                throw new InsufficientBalanceException("Insufficient balance in your account");

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
    public ResponseEntity<?> deleteExpenseByUsername(@PathVariable String username) throws InternalServerException {
        try {
            expenseService.deleteExpensesByUsername(username);
            return new ResponseEntity<>("Expenses deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerException("An error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/available-balance/{username}")
    public ResponseEntity<?> getAvailableBalance(@PathVariable String username) throws UserNotFound {
        try {
            if (loginAccountService.existsByUsername(username)) {
                double availableBalance = expenseService.getAvailableBalance(username);
                return new ResponseEntity<>(availableBalance, HttpStatus.OK);
            } else {
                throw new UserNotFound("User Not Found!");
            }
        } catch (Exception e) {
            throw new UserNotFound("User Not Found!");
        }
    }

    @GetMapping("/current-month/{username}")
    public ResponseEntity<ExpenseSummary> findCurrentMonthSummeryByUsername(@PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                ExpenseSummary expense = expenseService.findCurrentMonthSummeryByUsername(username);
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

    @GetMapping("/group_type-expense-sum/{username}/{startDate}/{endDate}")
    public ResponseEntity<?> getExpenseByTypeSpecificDuration(@PathVariable String username,
                                                              @PathVariable String startDate,
                                                              @PathVariable String endDate) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                List<ExpenseTypeSummery> expenseTypeSummeryList
                        = expenseService.findExpenseByType(username,
                        getLocalDateTime(startDate), getLocalDateTime(endDate));
                return new ResponseEntity<>(expenseTypeSummeryList, HttpStatus.OK);
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

    @GetMapping("/get-expense-by-duration/{username}/{startDate}/{endDate}")
    public ResponseEntity<?> getExpenseByDuration(@PathVariable String username,
                                                  @PathVariable String startDate,
                                                  @PathVariable String endDate) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                List<Expense> expenseTypeSummeryList
                        = expenseService.getExpenseByDuration(username,
                        getLocalDateTime(startDate), getLocalDateTime(endDate));
                return new ResponseEntity<>(expenseTypeSummeryList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
