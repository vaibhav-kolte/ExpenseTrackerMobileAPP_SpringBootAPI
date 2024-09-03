package com.myproject.expensetacker.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.myproject.expensetacker.R;
import com.myproject.expensetacker.databinding.ActivityMainBinding;
import com.myproject.expensetacker.databinding.NavHeaderMainBinding;
import com.myproject.expensetacker.repository.Database;
import com.myproject.expensetacker.repository.ExpenseAPI;
import com.myproject.expensetacker.repository.ExpenseAPIImpl;
import com.myproject.expensetacker.ui.gallery.GalleryFragment;
import com.myproject.expensetacker.ui.home.HomeFragment;
import com.myproject.expensetacker.ui.slideshow.SlideshowFragment;
import com.myproject.expensetacker.utils.PrintLog;
import com.myproject.expensetacker.utils.ShareData;
import com.myproject.expensetacker.utils.Utils;

import java.io.File;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Context context;
    private CircleImageView profilePhoto;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

//        binding.appBarMain.bottomNavigationView.setSelectedItemId(R.id.nav_home);

        context = MainActivity.this;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, new HomeFragment())
                .commit();

        binding.appBarMain.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.frame_layout, new HomeFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_expense) {
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.frame_layout, new GalleryFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_saving) {
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.frame_layout, new SlideshowFragment())
                        .commit();
                return true;
            }
            return false;
        });

        toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_my_expenses) {
                startActivity(new Intent(context, ShowExpensesActivity.class));
            } else if (id == R.id.nav_logout) {
                logoutUser();
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        updateNavigationHeaderLayout(binding.navView);
        downloadProfilePhoto();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // Check if the drawer is open
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Close the drawer
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Otherwise, proceed with the normal back button behavior
            super.onBackPressed();
        }
    }
    private void logoutUser() {
        ShareData shareData = new ShareData(context);
        shareData.clearAll();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateNavigationHeaderLayout(@NonNull NavigationView navigationView) {

        NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        ShareData shareData = new ShareData(context);
        headerBinding.tvUsername.setText(shareData.getString(ShareData.USERNAME, ""));

        ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getExtras() != null) {
                            Bitmap img = (Bitmap) data.getExtras().get("data");
                            if (img != null) {
                                PrintLog.infoLog("IMAGE", img.toString());
                                headerBinding.imgProfile.setImageBitmap(img);
                                uploadProfilePhoto(img);
                            }
                        }
                    }
                }
        );
        profilePhoto = headerBinding.imgProfile;
        headerBinding.imgProfile.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        });
    }

    private void downloadProfilePhoto() {
        ShareData shareData = new ShareData(getApplication().getApplicationContext());
        String username = shareData.getString(ShareData.USERNAME, "");

        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance(Database.RETROFIT);
        expenseAPIs.downloadImage(username + ".jpg", bitmap -> {
            if (profilePhoto != null) {
                profilePhoto.setImageBitmap(bitmap);
            }
        }, message -> PrintLog.errorLog(TAG, message));
    }

    private void uploadProfilePhoto(Bitmap bitmap) {

        ShareData shareData = new ShareData(getApplication().getApplicationContext());
        String username = shareData.getString(ShareData.USERNAME, "");
        File file = Utils.bitmapToFile(context, bitmap, username);

        ExpenseAPI expenseAPIs = ExpenseAPIImpl.getInstance(Database.RETROFIT);
        expenseAPIs.uploadProfilePhoto(username, file, () -> {
            Toast.makeText(context, "Image uploaded successfully",
                    Toast.LENGTH_SHORT).show();
        }, message -> {
            PrintLog.errorLog(TAG, "Exception: " + message);
        });
    }

}