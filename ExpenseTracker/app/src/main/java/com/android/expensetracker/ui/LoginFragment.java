package com.android.expensetracker.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.expensetracker.Account;
import com.android.expensetracker.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

    private SignInInterface signInInterface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        handleOnClickEvents();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            signInInterface = (SignInInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement SignInInterface");
        }
    }

    private void handleOnClickEvents() {
        binding.btnLogin.setOnClickListener(view -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            loginAccount(username, password);
        });

        binding.tvSignIn.setOnClickListener(view -> signInInterface.onSignIn());
    }

    void loginAccount(String username, String password) {
        if (!checkInputs(username, password)) return;
        Account loginAccount = new Account(username, password);
        loginAccount.login(getContext());
    }


    private boolean checkInputs(String username, String password) {
        boolean allInputIsValid = true;
        if (TextUtils.isEmpty(username)) {
            binding.textFieldUsername.setError("Username must not be empty");
            allInputIsValid = false;
        }
        if (TextUtils.isEmpty(password)) {
            binding.textFieldPassword.setError("Password must not be empty");
            allInputIsValid = false;
        }
        return allInputIsValid;
    }

    interface SignInInterface {
        void onSignIn();
    }
}