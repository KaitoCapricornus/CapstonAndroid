package com.example.capstonandroid.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Product>> mProduct;

    public HomeViewModel() {
        mProduct = new MutableLiveData<>();
        ProductDB db = new ProductDB("inventory/products");
        db.getAllProducts(new MyCallbackInterface<List<Product>>() {
            @Override
            public void onCallBack(List<Product> value) {
                mProduct.setValue(value);
            }
        });
    }

    public LiveData<List<Product>> getData() {
        return mProduct;
    }

}