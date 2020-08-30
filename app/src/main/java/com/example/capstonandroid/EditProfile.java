package com.example.capstonandroid;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView txtUsername, txtPhone, txtDob, txtEmail;
    Button btnCancel, btnEdit, btnPick;
    private ValueEventListener mListener;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        txtEmail = findViewById(R.id.txtEmail);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtDob = findViewById(R.id.txtDob);
        btnCancel = findViewById(R.id.btnCancel2);
        btnEdit = findViewById(R.id.btnEdit);
        btnPick = findViewById(R.id.btnPick);
        final SharedPreferences sharedPref = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        final String email = sharedPref.getString("email", "");
        final String pass = sharedPref.getString("pass", "");
        final String UID = sharedPref.getString("UID", "");

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User u = new User(txtUsername.getText().toString(), pass, txtEmail.getText().toString(), txtPhone.getText().toString(), txtDob.getText().toString());
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/identity/Customer");
//                mDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot ds : snapshot.getChildren()) {
//                            try {
//                                if (ds.child("email").getValue(String.class).equals(email)) {
//                                    key = ds.getKey();
//                                    mDatabase.removeEventListener(mListener);
//                                    break;
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });

                Map<String, Object> map = new HashMap();
                map.put(UID, u.toMap());
                mDatabase.updateChildren(map);
//                Toast.makeText(EditProfile.this, "Update success", Toast.LENGTH_LONG).show();
//                if (key != null) {
//                    mDatabase.child(key).setValue(u);
//                    Toast.makeText(EditProfile.this, "Update success", Toast.LENGTH_LONG).show();
//                }
                SharedPreferences.Editor session = sharedPref.edit();
                session.putString("email", email);
                session.putString("pass", pass);
                session.putString("dob", txtDob.getText().toString());
                session.putString("phone", txtPhone.getText().toString());
                session.putString("user_name", txtUsername.getText().toString());
                session.commit();
                Intent intent = new Intent(EditProfile.this, ProfileActivity.class);
                startActivity(intent);
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

    private void showDatePickerDialog() {
        DatePickerDialog d = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        d.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        txtDob.setText(date);
    }
}