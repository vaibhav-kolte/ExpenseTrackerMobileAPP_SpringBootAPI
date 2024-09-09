package com.myproject.expensetacker.ui.fragments;

import static com.myproject.expensetacker.utils.Constant.USED_DATABASE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.FragmentSignInBinding;
import com.myproject.expensetacker.model.Account;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;

import java.util.Objects;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private Context context;

    private boolean isShowingPassword = false;
    private boolean isShowingConfirmPassword = false;

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

    private void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleOnClickEvents() {
        binding.btnSignIn.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            String confirmPassword = Objects.requireNonNull(binding.etConfirmPassword.getText()).toString();
            if (password.equals(confirmPassword))
                signInAccount(username, password, confirmPassword);
            else binding.etConfirmPassword.setError("Password mismatch.");
        });

        binding.tvLoginIn.setOnClickListener(v -> loginInterface.onLogin());

        binding.etPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Get the width of the drawable on the right
                Drawable drawableRight = binding.etPassword.getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawableRight != null) {
                    int drawableWidth = drawableRight.getBounds().width();
                    // Check if the touch event was within the bounds of the drawable
                    if (event.getRawX() >= (binding.etPassword.getRight() - drawableWidth)) {
                        isShowingPassword = updateView(isShowingPassword, binding.etPassword);
                        return true;
                    }
                }
            }
            return false;
        });

        binding.etConfirmPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Get the width of the drawable on the right
                Drawable drawableRight = binding.etConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawableRight != null) {
                    int drawableWidth = drawableRight.getBounds().width();
                    // Check if the touch event was within the bounds of the drawable
                    if (event.getRawX() >= (binding.etConfirmPassword.getRight() - drawableWidth)) {
                        isShowingConfirmPassword = updateView(isShowingConfirmPassword, binding.etConfirmPassword);
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private boolean updateView(boolean isShowingPassword, TextInputEditText etPassword) {
        if (isShowingPassword) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.icon_eye_off, 0);
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.icon_eye, 0);
        }
        return !isShowingPassword;
    }

    private void signInAccount(String username, String password, String confirmPassword) {
        if (!checkInputs(username, password, confirmPassword)) return;
        Account account = new Account(username, password,"",null,null);
        showProgress();
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        expenseAPIs.signInAccount(account, () -> {
            hideProgress();
            Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
            loginInterface.onLogin();
        }, message -> {
            hideProgress();
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
        });
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
        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirmPassword);
        if (!password.equals(confirmPassword)) {
            binding.textFieldConfirmPassword.setError("Confirm password must be equal to password");
            allInputIsValid = false;
        }
        return allInputIsValid;
    }

    public interface LoginInterface {
        void onLogin();
    }
}