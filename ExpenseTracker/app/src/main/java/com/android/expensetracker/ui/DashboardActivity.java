package com.android.expensetracker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.android.expensetracker.R;
import com.android.expensetracker.databinding.ActivityDashboardBinding;
import com.android.expensetracker.repository.APICall;
import com.android.expensetracker.repository.AvailableBalanceInterface;
import com.android.expensetracker.utils.ShareData;
import com.google.android.material.navigation.NavigationView;


public class DashboardActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "DashboardActivity";

    private ActivityDashboardBinding binding;
    private Context context;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = DashboardActivity.this;

        handleOnClickEvents();
        showUsername();

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (binding.myDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.myDrawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            finish();
                        }
                    }
                });
    }

    private void handleOnClickEvents() {
        getMyBudget();
        handleDrawer();

        binding.swipeDown.setOnRefreshListener(this::getMyBudget);

        binding.btnAddBalance.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddBalanceActivity.class);
            startActivity(intent);
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddExpensesActivity.class);
            startActivity(intent);
        });
    }

    private void handleDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.myDrawerLayout, binding.toolbar,
                R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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
        binding.navigationView.setNavigationItemSelectedListener(this);
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_my_expenses) {
            startActivity(new Intent(this, ShowExpenseActivity.class));
        }

        binding.myDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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