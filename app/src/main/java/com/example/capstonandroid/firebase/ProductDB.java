package com.example.capstonandroid.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;
import com.google.firebase.FirebaseError;
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
                for(DataSnapshot ds : snapshot.getChildren()){
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
        final String temp_id = id;
        ref.orderByChild("productID").equalTo(id).addValueEventListener(new ValueEventListener() {

            /**
             * This method will be called with a snapshot of the data at this location. It will also be called
             * each time that data changes.
             *
             * @param snapshot The current data at the location
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    Product p = d.getValue(Product.class);
                    callback.onCallBack(p);
                    break;
                }
            }

            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase Database rules. For more information on
             * securing your data, see: <a
             * href="https://firebase.google.com/docs/database/security/quickstart" target="_blank"> Security
             * Quickstart</a>
             *
             * @param error A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
