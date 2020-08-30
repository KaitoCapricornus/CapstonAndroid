package com.example.capstonandroid.ui.order.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstonandroid.entity.Address;
import com.example.capstonandroid.firebase.AddressDB;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Address>> address;
    public MyCallbackInterface<List<Address>> callback;

    public MainViewModel() {
        address = new MutableLiveData<>();
        AddressDB db = new AddressDB("inventory/address");
        db.getAllAddress(new MyCallbackInterface<List<Address>>() {
            @Override
            public void onCallBack(List<Address> value) {
                address.setValue(value);
                callback.onCallBack(value);
            }
        });
    }

    public LiveData<List<Address>> getData() {
        return address;
    }
}