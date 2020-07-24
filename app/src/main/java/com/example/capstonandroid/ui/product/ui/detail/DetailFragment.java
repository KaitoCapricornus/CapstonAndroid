package com.example.capstonandroid.ui.product.ui.detail;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.capstonandroid.entity.Cart;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebaseinterface.Auxiliary;
import com.example.capstonandroid.ui.product.ProductActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        ProductActivity activity = (ProductActivity) getActivity();
        final String productID = activity.getProduct();

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
                if (s.getProductName().length() > 15) {
                    productName.setText(s.getProductName().substring(0, 15).concat("..."));
                } else {
                    productName.setText(s.getProductName());
                }
                productPrice.setText("Price: $" + s.getUnitPrice());
                if (s.getUnitInStock() > 0) {
                    status.setText("Available");
                    status.setTextColor(Color.GREEN);
                } else {
                    status.setText("Unavailable");
                    status.setTextColor(Color.RED);
                }
                description.setText(s.getDescription());
            }
        });

        final SharedPreferences sharedPref = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        final String user_name = sharedPref.getString("user_name", "");

        final FloatingActionButton floatingActionButton = root.findViewById(R.id.fabAddToCart);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailViewModel.getText(productID).observe(getViewLifecycleOwner(), new Observer<Product>() {
                    @Override
                    public void onChanged(@Nullable Product s) {
                        //long

                        int position = checkUserPosition(user_name, Auxiliary.carts);
                        if(position != -1) {
                            if(Auxiliary.carts.get(position).getProducts().size() != 0) {
                                if (!isExistProductID(productID, position, Auxiliary.carts)) {
                                    Auxiliary.cart.addProductToList(s, productID);
                                    Auxiliary.carts.add(Auxiliary.cart);
                                }
                            } else {
                                Auxiliary.cart.addProductToList(s, productID);
                                Auxiliary.carts.add(Auxiliary.cart);
                            }
                        } else {
                            Auxiliary.cart.addProductToList(s, productID);
                            Auxiliary.carts.add(Auxiliary.cart);
                        }
                    }
                });
            }
        });
        return root;
    }

    public boolean isExistProductID(String productID, int position, ArrayList<Cart> carts){
        for(int i = 0; i < carts.get(position).getProducts().size(); i++){
            if(carts.get(position).getProducts().get(i).getProductID().equalsIgnoreCase(productID)){
                return true;
            }
        }
        return false;
    }

    public int checkUserPosition(String UserName, List<Cart> carts){
        for(int i = 0; i < carts.size(); i++){
            if(carts.get(i).getUserName().equalsIgnoreCase(UserName)){
                return i;
            }
        }
        return -1;
    }
}