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
import com.android.expensetracker.databinding.FragmentSignInBinding;

import java.util.Objects;

public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";
    private FragmentSignInBinding binding;
    private Context context;

    private LoginInterface loginInterface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        handleOnClickEvents();
        context = requireContext();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            loginInterface = (LoginInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement SignInInterface");
        }
    }


    private void handleOnClickEvents() {
        binding.btnSignIn.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            String confirmPassword = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();
            signInAccount(username, password, confirmPassword);
        });

        binding.tvLoginIn.setOnClickListener(v -> loginInterface.onLogin());
    }

    private void signInAccount(String username, String password, String confirmPassword) {
        if (!checkInputs(username, password, confirmPassword)) return;

        Account account = new Account(username, password);
        account.signIn();
    }

    private boolean checkInputs(String username, String password, String confirmPassword) {
        boolean allInputIsValid = true;
        if (TextUtils.isEmpty(username)) {
            binding.textFieldUsername.setError("Username must not be empty");
            allInputIsValid = false;
        }
        if (TextUtils.isEmpty(password)) {
            binding.textFieldPassword.setError("Password must not be empty");
            allInputIsValid = false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.textFieldConfirmPassword.setError("Confirm password must not be empty");
            allInputIsValid = false;
        }
        if (password.equals(confirmPassword)) {
            binding.textFieldConfirmPassword.setError("Confirm password must be equal to password");
            allInputIsValid = false;
        }
        return allInputIsValid;
    }

    interface LoginInterface {
        void onLogin();
    }
}