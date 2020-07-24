package com.example.capstonandroid.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerCatalogAdapter;
import com.example.capstonandroid.adapter.RecyclerProductAdapter;
import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.CatalogDB;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.List;

public class SearchCatalogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_catalogs);

        final RecyclerView listViewCatalog = findViewById(R.id.recyclerCatalogSearch);
        final EditText search = findViewById(R.id.editTextSearchResult2);
        final Button searchButton = findViewById(R.id.buttonSearchResult2);
        final CatalogDB db = new CatalogDB("inventory/catalogs");
        String searchText = getIntent().getStringExtra("searchText");
        search.setText(searchText);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.getAllCatalogsByName(new MyCallbackInterface<List<Catalog>>(){
                    @Override
                    public void onCallBack(List<Catalog> value) {
                        RecyclerCatalogAdapter adapter = new RecyclerCatalogAdapter(value, SearchCatalogsActivity.this);
                        listViewCatalog.setAdapter(adapter);
                        listViewCatalog.invalidate();
                    }
                }, search.getText().toString());
            }
        });

        db.getAllCatalogsByName(new MyCallbackInterface<List<Catalog>>(){
            @Override
            public void onCallBack(List<Catalog> value) {
                RecyclerCatalogAdapter adapter = new RecyclerCatalogAdapter(value, SearchCatalogsActivity.this);
                listViewCatalog.setAdapter(adapter);
                listViewCatalog.setLayoutManager(new GridLayoutManager(SearchCatalogsActivity.this, 2, GridLayoutManager.VERTICAL, false));
            }
        }, search.getText().toString());
    }
}