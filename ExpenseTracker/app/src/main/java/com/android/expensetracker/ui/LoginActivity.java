package com.android.expensetracker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.android.expensetracker.R;
import com.android.expensetracker.databinding.ActivityLoginBinding;
import com.android.expensetracker.interfaces.NavigateInterface;


public class LoginActivity extends AppCompatActivity implements LoginFragment.SignInInterface,
        SignInFragment.LoginInterface,
        NavigateInterface {

    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = LoginActivity.this;

        updateLoginFragment();
    }

    @Override
    public void onSignIn() {
        updateSignInFragment();
    }

    @Override
    public void onLogin() {
        updateLoginFragment();
    }

    private void updateLoginFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, new LoginFragment())
                .commit();
    }

    private void updateSignInFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, new SignInFragment())
                .commit();
    }

    @Override
    public void navigateToDashboard() {
        startActivity(new Intent(context, DashboardActivity.class));
        finish();
    }
}