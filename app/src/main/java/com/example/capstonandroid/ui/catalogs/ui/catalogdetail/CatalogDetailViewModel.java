package com.example.capstonandroid.ui.catalogs.ui.catalogdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.CatalogDB;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.List;

public class CatalogDetailViewModel extends ViewModel {
    private MutableLiveData<List<Product>> mCatalog;
    private MutableLiveData<Catalog> catalogDetailViewModel;

    public CatalogDetailViewModel() {
        mCatalog = new MutableLiveData<>();
    }

    public LiveData<List<Product>> getData(String catalogID) {
        ProductDB db = new ProductDB("inventory/catalogs");
        db.getAllProductsByCatalogID(new MyCallbackInterface<List<Product>>() {
            @Override
            public void onCallBack(List<Product> value) {
                mCatalog.setValue(value);
            }
        }, catalogID);
        return mCatalog;
    }
    public LiveData<Catalog> getCatalog(String catalogID) {
        CatalogDB db = new CatalogDB("inventory/catalogs");
        db.getCatalogsByID(new MyCallbackInterface<Catalog>() {
            @Override
            public void onCallBack(Catalog value) {
                catalogDetailViewModel.setValue(value);
            }
        }, catalogID);
        return catalogDetailViewModel;
    }
}