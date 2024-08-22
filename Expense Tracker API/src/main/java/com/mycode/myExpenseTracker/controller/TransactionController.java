package com.mycode.myExpenseTracker.controller;

import com.mycode.myExpenseTracker.entities.Balance;
import com.mycode.myExpenseTracker.entities.MonthlyExpenseDTO;
import com.mycode.myExpenseTracker.model.Transaction;
import com.mycode.myExpenseTracker.entities.BalanceResponse;
import com.mycode.myExpenseTracker.model.ErrorResponse;
import com.mycode.myExpenseTracker.service.LoginAccountService;
import com.mycode.myExpenseTracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private LoginAccountService loginAccountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<Transaction> add(@RequestBody Transaction transaction) {
        try {
            if (transaction.getUsername().isEmpty()
                    || transaction.getTransactionType().isEmpty()
                    || String.valueOf(transaction.getTransactionAmount()).isEmpty()
                    || String.valueOf(transaction.getDate()).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (loginAccountService.existsByUsername(transaction.getUsername())) {
                transactionService.save(transaction);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll/{username}")
    public ResponseEntity<List<Transaction>> getByUsername(@PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                List<Transaction> transactions = transactionService.getByUsername(username);
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            System.out.println("Not found: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/available-balance/{username}")
    public ResponseEntity<?> getAvailableBalance(@PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                double availableBalance = transactionService.getAvailableBalance(username);
                BalanceResponse response = new BalanceResponse(HttpStatus.OK.value(),
                        availableBalance);
                return new ResponseEntity<>(response, HttpStatus.OK);
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

    @GetMapping("/balance/{username}")
    public ResponseEntity<?> getBalance(@PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                Balance creditBalance = transactionService.getBalance(username);

                return new ResponseEntity<>(creditBalance, HttpStatus.OK);
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

//    @GetMapping("/monthlyExpenses/{username}")
//    public ResponseEntity<?> findMonthlyExpenses(@PathVariable String username) {
//        try {
//            if (loginAccountService.existsByUsername(username)) {
//                List<MonthlyExpenseDTO> creditBalance = transactionService.findMonthlyExpenses(username);
//
//                return new ResponseEntity<>(creditBalance, HttpStatus.OK);
//            } else {
//                ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
//                        "User is not exits");
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    "An error occurred: " + e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{transactionType}/{username}")
    public ResponseEntity<?> getAllDebitTransaction(@PathVariable String transactionType,
                                                    @PathVariable String username) {
        try {
            if (loginAccountService.existsByUsername(username)) {
                List<Transaction> debitTransaction = transactionService.getTransactionsForUser(transactionType, username);
                return new ResponseEntity<>(debitTransaction, HttpStatus.OK);
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

    @DeleteMapping("/delete-transaction/{username}")
    public ResponseEntity<?> deleteTransactionByUsername(@PathVariable String username) {
        try {
            transactionService.deleteTransactionByUsername(username);
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "Expenses deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
