package com.example.capstonandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.User;


public class ProfileActivity extends AppCompatActivity {

    TextView name,mobileNumber,txtdob,txtemail, txtLogOut;
    ImageView Profile;
    //CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        map();
        final SharedPreferences sharedPref = getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        String dob = sharedPref.getString("dob", "");
        String email = sharedPref.getString("email", "");
        String phone = sharedPref.getString("phone", "");
        String user_name = sharedPref.getString("user_name", "");
        name.setText(user_name);
        mobileNumber.setText(phone);
        txtdob.setText(dob);
        txtemail.setText(email);
        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to Log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });
    }

    private void map() {
        name = findViewById(R.id.name);
        mobileNumber = findViewById(R.id.mobileNumber);
        txtdob = findViewById(R.id.dob);
        txtemail = findViewById(R.id.email);
        txtLogOut=findViewById(R.id.txtLogout);
        Profile = findViewById(R.id.edit2);
    }
}
