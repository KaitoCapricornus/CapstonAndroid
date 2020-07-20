package com.example.capstonandroid.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerProductAdapter;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final RecyclerView listViewProducts = findViewById(R.id.recyclerProductSearchView);
        final EditText search = findViewById(R.id.editTextSearchResult);
        final Button searchButton = findViewById(R.id.buttonSearchResult);
        search.setText(getIntent().getStringExtra("searchText"));
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchText = search.getText().toString();
                //recreate activity with new searchText
            }
        });
        ProductDB db = new ProductDB("inventory/products");
        db.getAllProductsByName(new MyCallbackInterface<List<Product>>(){
            @Override
            public void onCallBack(List<Product> value) {
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(value, SearchActivity.this);
                listViewProducts.setAdapter(adapter);
                listViewProducts.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            }
        }, search.getText().toString());

    }
}