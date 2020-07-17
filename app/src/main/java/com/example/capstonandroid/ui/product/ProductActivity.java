package com.example.capstonandroid.ui.product;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.capstonandroid.R;
import com.example.capstonandroid.ui.product.ui.detail.DetailFragment;
import com.example.capstonandroid.ui.product.ui.judge.JudgeFragment;
import com.example.capstonandroid.ui.product.ui.relate.RelateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ProductActivity extends AppCompatActivity {

    private String productID;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productID = getIntent().getStringExtra("data");
        toolbar = getSupportActionBar();
        toolbar.setTitle("Tổng quan");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_detail, R.id.navigation_judge, R.id.navigation_relate)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public String getProduct() {
        return productID;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_detail:
                    toolbar.setTitle("Tổng quan");
                    fragment = new DetailFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_judge:
                    toolbar.setTitle("Đánh giá & nhận xét");
                    fragment = new JudgeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_relate:
                    toolbar.setTitle("Liên quan");
                    fragment = new RelateFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
