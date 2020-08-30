package com.example.capstonandroid.ui.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstonandroid.R;
import com.example.capstonandroid.ui.order.ui.main.OrderActivityThucFragment;

public class OrderActivityThuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_thuc_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OrderActivityThucFragment.newInstance())
                    .commitNow();
        }
    }
}