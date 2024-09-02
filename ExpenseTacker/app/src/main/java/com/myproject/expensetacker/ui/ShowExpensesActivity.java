package com.myproject.expensetacker.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.ActivityShowExpensesBinding;

public class ShowExpensesActivity extends AppCompatActivity {
    private static final String TAG = "ShowExpensesActivity";
    private ActivityShowExpensesBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ShowExpensesActivity.this;

        
    }
}