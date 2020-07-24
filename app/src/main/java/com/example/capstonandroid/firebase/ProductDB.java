package com.example.capstonandroid.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductDB {
    private String referencePath;
    private DatabaseReference ref;

    public ProductDB(String referencePath) {
        this.referencePath = referencePath;
        this.ref = FirebaseDatabase.getInstance().getReference(this.referencePath);
    }

    public DatabaseReference getRef() {
        return ref;
    }

    public void getAllProducts(final MyCallbackInterface<List<Product>> callback) {
        final List<Product> output = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    output.add(product);
                }
                callback.onCallBack(output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllProductsByName(final MyCallbackInterface<List<Product>> callback, final String name) {
        final List<Product> output = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    if(product.getProductName().toLowerCase().contains(name.toLowerCase())){
                        output.add(product);
                    }
                }
                callback.onCallBack(output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllProductsByCatalogID(final MyCallbackInterface<List<Product>> callback, final String catalogID) {
        final List<Product> output = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    if(product.getCatalogs().toLowerCase().equals(catalogID.toLowerCase())){
                        output.add(product);
                    }
                }
                callback.onCallBack(output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getProductByID(final MyCallbackInterface<Product> callback, String id) {
        ref.orderByChild("productID").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    Product product = child.getValue(Product.class);
                    callback.onCallBack(product);
                    break;
                }
                Log.i("p", snapshot.getValue(Product.class) + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
