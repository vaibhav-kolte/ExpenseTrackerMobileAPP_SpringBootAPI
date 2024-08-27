package com.mycode.myExpenseTracker.service;

import com.mycode.myExpenseTracker.model.LoginAccount;
import com.mycode.myExpenseTracker.repository.LoginAccountRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginAccountService {
    private static final Logger logger = LoggerFactory.getLogger(LoginAccountService.class);

    @Autowired
    private LoginAccountRepository loginAccountRepository;

    public List<LoginAccount> listAll() {
        return loginAccountRepository.findAll();
    }

    public void save(LoginAccount loginAccount) {
        loginAccountRepository.save(loginAccount);
    }

    public LoginAccount getByUsername(String username) {
        return loginAccountRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return loginAccountRepository.existsByUsername(username);
    }

    @Transactional
    public String deleteByUsername(String username) {
        try {
            if (username == null || username.isEmpty()) {
                return "Invalid username";
            }
            if (!loginAccountRepository.existsByUsername(username)) {
                return "Username not found";
            }
            loginAccountRepository.deleteByUsername(username);
            return "Login Account Deleted";
        } catch (Exception e) {
            logger.error("Error deleting login account with username: {}", username, e);
            return "Error deleting login account";
        }
    }

    public boolean userExists(String username) {
        return loginAccountRepository.existsByUsername(username);// ? 1 : 0;
    }

    public boolean existUser(String username, String password) {
        return loginAccountRepository.existUser(username, password);// ? 1 : 0;
    }

}
