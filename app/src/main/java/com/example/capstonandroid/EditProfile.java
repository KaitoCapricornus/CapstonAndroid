package com.example.capstonandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    TextView txtUsername, txtPhone, txtDob;
    Button btnCancel, btnEdit;
    private ValueEventListener mListener;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txtUsername=findViewById(R.id.txtUsername);
        txtPhone=findViewById(R.id.txtPhone);
        txtDob=findViewById(R.id.txtDob);
        btnCancel=findViewById(R.id.btnCancel);
        btnEdit=findViewById(R.id.btnEdit);
        final SharedPreferences sharedPref = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        final String email = sharedPref.getString("email", "");
        final String pass=sharedPref.getString("pass", "");


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u=new User(txtUsername.getText().toString(), pass, email, txtPhone.getText().toString(), txtDob.getText().toString());
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/identity/Customer");
                mListener=mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            try {
                                if (ds.child("email").getValue(String.class).equals(email)) {
                                    key=ds.getKey();
                                    mDatabase.removeEventListener(mListener);
                                    break;
                                }
                            } catch (Exception e) {
                            }
                        }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                if (key!=null) {
                    mDatabase.child(key).setValue(u);
                }
                SharedPreferences.Editor session = sharedPref.edit();
                session.putString("email", email);
                session.putString("pass", pass);
                session.putString("dob", txtDob.getText().toString());
                session.putString("phone", txtPhone.getText().toString());
                session.putString("user_name", txtUsername.getText().toString());
                session.commit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}