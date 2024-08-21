package com.myproject.expensetacker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.ActivityLoginBinding;
import com.myproject.expensetacker.interfaces.NavigateInterface;

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

    @Override
    public void navigateToDashboard() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
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
}