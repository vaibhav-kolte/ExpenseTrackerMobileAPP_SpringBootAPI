package com.myproject.expensetacker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.ActivityLoginBinding;
import com.myproject.expensetacker.interfaces.NavigateInterface;
import com.myproject.expensetacker.ui.fragments.LoginFragment;
import com.myproject.expensetacker.ui.fragments.SignInFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.SignInInterface,
        SignInFragment.LoginInterface,
        NavigateInterface {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = LoginActivity.this;

        updateFragment(new LoginFragment());
    }

    @Override
    public void onSignIn() {
        updateFragment(new SignInFragment());
    }

    @Override
    public void onLogin() {
        updateFragment(new LoginFragment());
    }

    @Override
    public void navigateToDashboard() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    private void updateFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }

}