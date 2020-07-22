package com.example.capstonandroid.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerProductAdapter;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.ui.search.SearchProductsActivity;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final RecyclerView listViewProducts = root.findViewById(R.id.recyclerProductView);
        final EditText search = root.findViewById(R.id.editTextSearch);
        final Button searchButton = root.findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchText = search.getText().toString();
                Intent intent = new Intent(container.getContext(), SearchProductsActivity.class);
                intent.putExtra("searchText", searchText);
                startActivity(intent);
            }
        });

        homeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> s) {
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(s, container.getContext());
                listViewProducts.setAdapter(adapter);
                listViewProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        return root;
    }


}