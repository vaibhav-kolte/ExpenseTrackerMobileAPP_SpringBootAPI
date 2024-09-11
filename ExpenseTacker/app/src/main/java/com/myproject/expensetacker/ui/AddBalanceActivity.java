package com.myproject.expensetacker.ui;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.myproject.expensetacker.databinding.ActivityAddBalanceBinding;
import com.myproject.expensetacker.model.MyExpenses;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.utils.ShareData;

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
        AddBalanceActivity.this.setTitle("Add Balance");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        handleClickEvents();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

            MyExpenses myExpenses = new MyExpenses(username, "", amount,formattedDate ,
                    "","CREDIT");
            addBalance(myExpenses);

        });
    }

    private void addBalance(MyExpenses myExpenses){
        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance();
        expenseAPIs.addExpense(myExpenses, this::finish, message -> {
            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
        });
    }
}