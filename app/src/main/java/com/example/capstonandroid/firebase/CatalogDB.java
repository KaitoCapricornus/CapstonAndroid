package com.example.capstonandroid.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CatalogDB {
    private DatabaseReference ref;

    public CatalogDB(String ref) {
        this.ref = FirebaseDatabase.getInstance().getReference(ref);
    }

    public void getAllCatalog(final MyCallbackInterface<List<Catalog>> callback) {
        final List<Catalog> output = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Catalog catalog = ds.getValue(Catalog.class);
                    output.add(catalog);
                }
                callback.onCallBack(output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllCatalogsByName(final MyCallbackInterface<List<Catalog>> callback, final String name) {
        final List<Catalog> output = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Catalog catalog = ds.getValue(Catalog.class);
                    if(catalog.getCatalogName().toLowerCase().contains(name.toLowerCase())){
                        output.add(catalog);
                    }
                }
                callback.onCallBack(output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getCatalogsByID(final MyCallbackInterface<Catalog> callback, final String id) {
        ref.orderByChild("catalogID").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    Catalog product = child.getValue(Catalog.class);
                    callback.onCallBack(product);
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
