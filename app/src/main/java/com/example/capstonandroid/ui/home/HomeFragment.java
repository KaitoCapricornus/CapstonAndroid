package com.example.capstonandroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerProductAdapter;
import com.example.capstonandroid.asynctask.DownloadImageAsyncTask;
import com.example.capstonandroid.entity.Product;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textViewID = root.findViewById(R.id.textViewProductID);
//        final TextView textViewPrice = root.findViewById(R.id.textViewPrice);
//        final ImageView imageViewProductImage = root.findViewById(R.id.imageViewProductImage);
        final RecyclerView listViewProducts = root.findViewById(R.id.recyclerProductView);

        homeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> s) {
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(s);
                listViewProducts.setAdapter(adapter);
                listViewProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        return root;
    }

}