package com.example.capstonandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.Cart;
import com.example.capstonandroid.firebaseinterface.Auxiliary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    Button btn_login;
    TextView register,fogot;
    processDialog processDialog = new processDialog(this);
    private ValueEventListener mListener;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/identity/Customer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login);
        map();
        SharedPreferences sharedPreferences = getSharedPreferences("datalogin",MODE_PRIVATE);
        final SharedPreferences.Editor session = sharedPreferences.edit();

        String loginData = sharedPreferences.getString("user_name", "");
        if(!"".equals(loginData)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });

        fogot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString();
                final String Pass = pass.getText().toString();
                final boolean[] check = {false};
                processDialog.start();
                final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                mListener=mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            try {
                                if (ds.child("email").getValue(String.class).equals(Email) && ds.child("pass").getValue(String.class).equals(Pass)) {
                                    check[0] = true;
                                    session.putString("email", ds.child("email").getValue(String.class));
                                    session.putString("pass", ds.child("pass").getValue(String.class));
                                    session.putString("dob", ds.child("dob").getValue(String.class));
                                    session.putString("phone", ds.child("phone").getValue(String.class));
                                    session.putString("user_name", ds.child("username").getValue(String.class));
                                    session.commit();

                                    final SharedPreferences sharedPref = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
                                    final String user_name = sharedPref.getString("user_name", "");
                                    Auxiliary.carts = new ArrayList<>();
                                    Auxiliary.cart = new Cart(user_name);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    stopListener();
                                    finish();
                                    break;
                                }
                            } catch (Exception e) {
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Server is Disable");
                                alertDialog.show();
                                processDialog.end();
                                stopListener();
                                break;
                            }
                        }
                        if (check[0] == false) {
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Wrong username or password!");
                            stopListener();
                            processDialog.end();
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void map(){
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btn_login = findViewById(R.id.btn_login);
        register = findViewById(R.id.register);
        fogot = findViewById(R.id.fogot);
    }

    public void stopListener(){
        mDatabase.removeEventListener(mListener);
    }
}

