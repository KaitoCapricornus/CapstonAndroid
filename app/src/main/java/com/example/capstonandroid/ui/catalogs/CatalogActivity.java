package com.example.capstonandroid.ui.catalogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstonandroid.R;
import com.example.capstonandroid.ui.catalogs.ui.catalogdetail.CatalogDetailFragment;

public class CatalogActivity extends AppCompatActivity {

    private String catalogID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_activity);
        catalogID = getIntent().getStringExtra("data");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerCatalog, CatalogDetailFragment.newInstance())
                    .commitNow();
        }
    }

    public String getCatalogID() {
        return catalogID;
    }
}