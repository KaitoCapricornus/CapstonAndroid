package com.example.capstonandroid.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SearchProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);


        final RecyclerView listViewProducts = findViewById(R.id.recyclerProductSearch);
        final EditText search = findViewById(R.id.editTextSearchResult);
        final Button searchButton = findViewById(R.id.buttonSearchResult);
        final ProductDB db = new ProductDB("inventory/products");
        String searchText = getIntent().getStringExtra("searchText");
        search.setText(searchText);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.getAllProductsByName(new MyCallbackInterface<List<Product>>(){
                    @Override
                    public void onCallBack(List<Product> value) {
                        RecyclerProductAdapter adapter = new RecyclerProductAdapter(value, SearchProductsActivity.this);
                        listViewProducts.setAdapter(adapter);
                        listViewProducts.invalidate();
                    }
                }, search.getText().toString());
            }
        });

        db.getAllProductsByName(new MyCallbackInterface<List<Product>>(){
            @Override
            public void onCallBack(List<Product> value) {
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(value, SearchProductsActivity.this);
                listViewProducts.setAdapter(adapter);
                listViewProducts.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));
            }
        }, search.getText().toString());

    }
}