package com.android.expensetracker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.expensetracker.R;
import com.android.expensetracker.utils.ShareData;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = SplashActivity.this;
        openNextPage();
    }

    private void openNextPage() {
        new Handler().postDelayed(() -> {
            ShareData shareData = new ShareData(context);
            boolean isUserLoggedIn = shareData.getBoolean(ShareData.IS_LOGIN, false);
            Intent intent;
            if (isUserLoggedIn) {
                intent = new Intent(context, DashboardActivity.class);
            } else {
                intent = new Intent(context, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1000);
    }
}