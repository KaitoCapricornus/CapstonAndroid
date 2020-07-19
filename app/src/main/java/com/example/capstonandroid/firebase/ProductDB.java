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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Map<String, Product> td = (HashMap<String, Product>) snapshot.getValue();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product product = ds.getValue(Product.class);
                    output.add(product);
                }
                Log.i("catalog", td.toString());
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
