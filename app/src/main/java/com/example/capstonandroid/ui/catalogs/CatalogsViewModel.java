package com.example.capstonandroid.ui.catalogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.CatalogDB;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.List;

public class CatalogsViewModel extends ViewModel {

    private MutableLiveData<List<Catalog>> mCatalog;

    public CatalogsViewModel() {
        mCatalog = new MutableLiveData<>();
        CatalogDB db = new CatalogDB("inventory/catalogs");
        db.getAllCatalog(new MyCallbackInterface<List<Catalog>>() {
            @Override
            public void onCallBack(List<Catalog> value) {
                mCatalog.setValue(value);
            }
        });
    }

    public LiveData<List<Catalog>> getData() {
        return mCatalog;
    }
}