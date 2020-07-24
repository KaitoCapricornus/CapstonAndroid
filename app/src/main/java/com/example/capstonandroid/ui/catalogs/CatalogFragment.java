package com.example.capstonandroid.ui.catalogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerCatalogAdapter;
import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.ui.search.SearchProductsActivity;

import java.util.List;

public class CatalogFragment extends Fragment {

    private CatalogsViewModel catalogsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        catalogsViewModel =
                ViewModelProviders.of(this).get(CatalogsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_catalogs, container, false);
        final RecyclerView listViewCatalogs = root.findViewById(R.id.recyclerCatalog);
        final EditText search = root.findViewById(R.id.editTextSearchCatalog);
        final Button searchButton = root.findViewById(R.id.buttonSearchCatalog);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchText = search.getText().toString();
                Intent intent = new Intent(container.getContext(), SearchProductsActivity.class);
                intent.putExtra("searchText", searchText);
                startActivity(intent);
            }
        });

        catalogsViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Catalog>>() {
            @Override
            public void onChanged(@Nullable List<Catalog> s) {
                RecyclerCatalogAdapter adapter = new RecyclerCatalogAdapter(s, container.getContext());
                listViewCatalogs.setAdapter(adapter);
                listViewCatalogs.setLayoutManager(new GridLayoutManager(container.getContext(), 2, GridLayoutManager.VERTICAL, false));
            }
        });
        return root;
    }
}