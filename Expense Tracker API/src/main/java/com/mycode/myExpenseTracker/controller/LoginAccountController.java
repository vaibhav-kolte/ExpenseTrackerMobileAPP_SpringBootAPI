package com.mycode.myExpenseTracker.controller;

import com.mycode.myExpenseTracker.exceptions.InvalidInputException;
import com.mycode.myExpenseTracker.exceptions.UserAlreadyExistException;
import com.mycode.myExpenseTracker.exceptions.UserNotFound;
import com.mycode.myExpenseTracker.model.LoginAccount;
import com.mycode.myExpenseTracker.service.ExpenseService;
import com.mycode.myExpenseTracker.service.LoginAccountService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/login")
public class LoginAccountController {

    @Autowired
    private LoginAccountService loginAccountService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/getAll")
    public List<LoginAccount> list() {
        return loginAccountService.listAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LoginAccount loginAccount) throws InvalidInputException, UserAlreadyExistException {
        if (loginAccount.getUsername().isEmpty() || loginAccount.getMyPassword().isEmpty()) {
            throw new InvalidInputException("Username and password should not empty");
        }
        if (loginAccountService.existsByUsername(loginAccount.getUsername())) {
            throw new UserAlreadyExistException("This username already exits");
        }
        loginAccountService.save(loginAccount);
        return new ResponseEntity<>(loginAccount, HttpStatus.OK);
    }

    @GetMapping("/userExits/{username}")
    public ResponseEntity<?> userExits(@PathVariable String username) {
        boolean isUserExits = loginAccountService.userExists(username);
        return new ResponseEntity<>(isUserExits, HttpStatus.OK);
    }

    @GetMapping("/userExits/{username}/{password}")
    public ResponseEntity<?> userExits(@PathVariable String username, @PathVariable String password) {
        boolean isUserExits = loginAccountService.existUser(username, password);
        return new ResponseEntity<>(isUserExits, HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) throws UserNotFound {
        try {
            LoginAccount loginAccount = loginAccountService.getByUsername(username);
            System.out.println("Username : " + loginAccount.getUsername());
            return new ResponseEntity<>(loginAccount, HttpStatus.OK);

        } catch (NullPointerException | NoSuchElementException e) {
            throw new UserNotFound("User Not Found!");
        }
    }

    @DeleteMapping("/delete/{username}")
    public String deleteByUsername(@PathVariable String username) {
        expenseService.deleteExpensesByUsername(username);
        return loginAccountService.deleteByUsername(username);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<LoginAccount> update(@RequestBody LoginAccount loginAccount,
                                               @PathVariable String username) {
        try {
            if (loginAccount.getUsername().isEmpty() || loginAccount.getMyPassword().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (loginAccountService.existsByUsername(username)) {
                loginAccountService.save(loginAccount);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Resource resource = new ClassPathResource("profile_imgs/" + imageName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
