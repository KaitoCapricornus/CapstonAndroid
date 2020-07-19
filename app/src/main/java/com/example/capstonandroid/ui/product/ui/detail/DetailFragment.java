package com.example.capstonandroid.ui.product.ui.detail;

import android.graphics.Color;
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

    private DetailViewModel detailViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        ProductActivity activity = (ProductActivity) getActivity();
        String productID = activity.getProduct();

        final ImageView image = root.findViewById(R.id.imageViewProduct);
        final TextView productName = root.findViewById(R.id.textViewProductNameDetail);
        final TextView productPrice = root.findViewById(R.id.textViewProductPriceDetail);
        final TextView status = root.findViewById(R.id.textViewStatus);
        final TextView description = root.findViewById(R.id.textViewDescription);

        detailViewModel.getText(productID).observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product s) {
                DownloadImageAsyncTask imageAsyncTask = new DownloadImageAsyncTask(image);
                imageAsyncTask.execute(s.getProductImage());
                productName.setText(s.getProductName());
                productPrice.setText("Price: $" + s.getUnitPrice());
                if(s.getUnitInStock() > 0){
                    status.setText("Available");
                    status.setTextColor(Color.GREEN);
                }else{
                    status.setText("Unavailable");
                    status.setTextColor(Color.RED);
                }
                description.setText(s.getDescription());
            }
        });
        return root;
    }
}