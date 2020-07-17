package com.example.capstonandroid.ui.product.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.capstonandroid.R;
import com.example.capstonandroid.asynctask.DownloadImageAsyncTask;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.ui.product.ProductActivity;

public class DetailFragment extends Fragment {

    private DetailViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        ProductActivity activity = (ProductActivity) getActivity();
        String productID = activity.getProduct();
        homeViewModel.setProductID(productID);

        final ImageView image = root.findViewById(R.id.imageViewProductImage);
        final TextView productName = root.findViewById(R.id.textViewProductNameDetail);
        final TextView productPrice = root.findViewById(R.id.textViewProductPrice);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product s) {
                DownloadImageAsyncTask imageAsyncTask = new DownloadImageAsyncTask(image);
                imageAsyncTask.execute(s.getProductImage());
                productName.setText(s.getProductName());
                productPrice.setText(s.getUnitPrice());
            }
        });
        return root;
    }
}