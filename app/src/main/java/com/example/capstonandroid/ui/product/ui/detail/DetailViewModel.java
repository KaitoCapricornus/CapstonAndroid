package com.example.capstonandroid.ui.product.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<Product> mData;
    private String productID;

    public DetailViewModel() {
        mData = new MutableLiveData<>();
        ProductDB db = new ProductDB("inventory/products");
        db.getProductByID(new MyCallbackInterface<Product>() {
            @Override
            public void onCallBack(Product value) {
                mData.setValue(value);
            }
        }, productID);
    }

    public LiveData<Product> getText() {
        return mData;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}