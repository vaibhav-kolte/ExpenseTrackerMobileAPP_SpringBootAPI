package com.android.expensetracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.expensetracker.databinding.ActivityAddBalanceBinding;
import com.android.expensetracker.models.AddBalance;
import com.android.expensetracker.repository.APICall;
import com.android.expensetracker.repository.APICallInterface;
import com.android.expensetracker.utils.ShareData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AddBalanceActivity extends AppCompatActivity {

    private static final String TAG = "AddBalanceActivity";
    private ActivityAddBalanceBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBalanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = AddBalanceActivity.this;

        handleClickEvents();
    }

    private void handleClickEvents() {
        binding.btnAdd.setOnClickListener(view -> {
            String amountInString = Objects.requireNonNull(binding.etAmount.getText()).toString();
            if (TextUtils.isEmpty(amountInString)) {
                binding.textFieldAmount.setError("Add amount");
                return;
            }
            double amount = Double.parseDouble(amountInString);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = today.format(formatter);
            ShareData shareData = new ShareData(context);
            String username = shareData.getString(ShareData.USERNAME, "");

            AddBalance addBalance = new AddBalance(username, formattedDate, amount, "CREDIT");
            System.out.println(addBalance);

            APICall apiCall = new APICall(context);
            apiCall.addBalance(addBalance, new APICallInterface() {
                @Override
                public void onSuccess() {
                    finish();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}