package com.myproject.expensetacker.ui;

import android.os.Bundle;
import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.myproject.expensetacker.databinding.ActivityAddTransactionBinding;
import com.myproject.expensetacker.ui.fragments.MainFragment;

import java.util.ArrayList;
import java.util.Objects;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "AddTransactionActivity";
    private ActivityAddTransactionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored){}
        setContentView(binding.getRoot());

        ArrayList<String> arrayList = new ArrayList<>(0);

        arrayList.add("Income");
        arrayList.add("Expense");
        arrayList.add("Transfer");
        arrayList.add("Invest");

        binding.tabLayout.setupWithViewPager(binding.viewPager);

        prepareViewPager(binding.viewPager, arrayList);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());

        MainFragment mainFragment = new MainFragment();

        for (int i = 0; i < arrayList.size(); i++) {
            Bundle bundle = new Bundle();

            bundle.putString("title", arrayList.get(i));

            mainFragment.setArguments(bundle);

            adapter.addFragment(mainFragment, arrayList.get(i));
            mainFragment = new MainFragment();
        }
        // set adapter
        viewPager.setAdapter(adapter);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        // Initialize arrayList
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();


        // Create constructor
        public void addFragment(Fragment fragment, String s) {
            // Add fragment
            fragmentArrayList.add(fragment);
            // Add title
            stringArrayList.add(s);
        }

        public MainAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // return fragment position
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            // Return fragment array list size
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return new SpannableString(stringArrayList.get(position));
        }
    }

}