package com.android.expensetracker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.android.expensetracker.R;
import com.android.expensetracker.databinding.ActivityDashboardBinding;
import com.android.expensetracker.repository.APICall;
import com.android.expensetracker.repository.AvailableBalanceInterface;
import com.android.expensetracker.utils.ShareData;


public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    private ActivityDashboardBinding binding;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = DashboardActivity.this;

        handleOnClickEvents();
        showUsername();
    }

    private void handleOnClickEvents() {
        getMyBudget();

        binding.swipeDown.setOnRefreshListener(this::getMyBudget);

        binding.floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddBalanceActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMyBudget();
    }

    private void showUsername() {
        ShareData shareData = new ShareData(context);
        String username = shareData.getString(ShareData.USERNAME, "");
        binding.toolbar.setTitle(TextUtils.isEmpty(username) ? "Welcome" : username);
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        ShareData shareData = new ShareData(context);
        shareData.clearAll();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void getMyBudget() {
        APICall apiCall = new APICall(context);
        apiCall.getAvailableBalance(new AvailableBalanceInterface() {
            @Override
            public void onSuccess(double balance) {
                binding.tvMyBudget.setText(String.valueOf(balance));
                binding.swipeDown.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                binding.tvMyBudget.setText("0.0");
            }
        });
    }
}