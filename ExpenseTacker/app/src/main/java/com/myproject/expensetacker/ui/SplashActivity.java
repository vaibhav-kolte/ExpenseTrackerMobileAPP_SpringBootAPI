package com.myproject.expensetacker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.utils.ShareData;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = SplashActivity.this;
        openNextPage();
    }

    private void openNextPage() {
        new Handler().postDelayed(() -> {
            ShareData shareData = new ShareData(context);
            boolean isUserLoggedIn = shareData.getBoolean(ShareData.IS_LOGIN, false);
            Intent intent;
            if (isUserLoggedIn) {
                intent = new Intent(context, MainActivity.class);
            } else {
                intent = new Intent(context, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1000);
    }
}