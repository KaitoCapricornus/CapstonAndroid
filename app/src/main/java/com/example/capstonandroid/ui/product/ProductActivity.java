package com.example.capstonandroid.ui.product;

import android.os.Bundle;

import com.example.capstonandroid.R;
import com.example.capstonandroid.ui.product.ui.detail.DetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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

        BottomNavigationView navView = findViewById(R.id.nav_view_product);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_detail, R.id.navigation_comment, R.id.navigation_relate)
                .build();
        NavController navController = Navigation.findNavController(ProductActivity.this, R.id.nav_host_fragment_product);
        NavigationUI.setupActionBarWithNavController(ProductActivity.this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setSelectedItemId(R.id.navigation_detail);
    }

    public String getProduct() {
        return productID;
    }

}
