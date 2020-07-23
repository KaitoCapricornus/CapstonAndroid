package com.example.capstonandroid.firebase;

import androidx.annotation.NonNull;

import com.example.capstonandroid.entity.Address;
import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddressDB {
    private DatabaseReference ref;

    public AddressDB(String ref) {
        this.ref = FirebaseDatabase.getInstance().getReference(ref);
    }

    public void getAllAddress(final MyCallbackInterface<List<Address>> callback) {
        final List<Address> output = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Address address = ds.getValue(Address.class);
                    output.add(address);
                }
                callback.onCallBack(output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
