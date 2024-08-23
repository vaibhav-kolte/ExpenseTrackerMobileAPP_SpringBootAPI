package com.myproject.expensetacker.ui;

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

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.FragmentLoginBinding;
import com.myproject.expensetacker.interfaces.NavigateInterface;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.PrintLog;
import com.myproject.expensetacker.utils.ShareData;

import java.util.Objects;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

    private SignInInterface signInInterface;
    private NavigateInterface navigateInterface;

    private boolean isShowingPassword = false;

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
            navigateInterface = (NavigateInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement SignInInterface");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleOnClickEvents() {
        binding.btnLogin.setOnClickListener(view -> {
            String username = Objects.requireNonNull(binding.etUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            loginAccount(username, password);
        });


        binding.tvSignIn.setOnClickListener(view -> signInInterface.onSignIn());


        binding.etPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Get the width of the drawable on the right
                Drawable drawableRight = binding.etPassword.getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawableRight != null) {
                    int drawableWidth = drawableRight.getBounds().width();
                    // Check if the touch event was within the bounds of the drawable
                    if (event.getRawX() >= (binding.etPassword.getRight() - drawableWidth)) {
                        if (isShowingPassword) {
                            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.icon_eye_off, 0);
                        } else {
                            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.icon_eye, 0);
                        }
                        isShowingPassword = !isShowingPassword;
                        return true;
                    }
                }
            }
            return false;
        });

    }

    void loginAccount(String username, String password) {
        if (!checkInputs(username, password)) return;
        showProgress();
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance(Database.RETROFIT);

        expenseAPIs.loggedInAccount(username, account -> {
            hideProgress();
            if (username.equals(account.getUsername()) && password.equals(account.getMyPassword())) {
                ShareData shareData = new ShareData(getContext());
                shareData.putBoolean(ShareData.IS_LOGIN, true);
                shareData.putString(ShareData.USERNAME, account.getUsername());
                navigateInterface.navigateToDashboard();
            } else {
                Toast.makeText(getContext(), "Check your credentials", Toast.LENGTH_SHORT).show();
            }
        }, message -> {
            hideProgress();
            PrintLog.errorLog(TAG, "Check user Exception: " + message);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }


    private void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
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