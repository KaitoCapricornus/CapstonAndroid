package com.example.capstonandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.User;

public class LoginMainActivity extends AppCompatActivity {

    Button viewProfile, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        viewProfile = findViewById(R.id.viewProfile);
        btn_logout = findViewById(R.id.btn_logout);
        final SharedPreferences sharedPref = getSharedPreferences("datalogin", Context.MODE_PRIVATE);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LoginMainActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to Log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(LoginMainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dob = sharedPref.getString("dob", "");
                String email = sharedPref.getString("email", "");
                String pass = sharedPref.getString("pass", "");
                String phone = sharedPref.getString("phone", "");
                String user_name = sharedPref.getString("user_name", "");
                Intent intent = new Intent(LoginMainActivity.this,ProfileActivity.class);
                User user = new User(user_name,pass,email,phone,dob);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}