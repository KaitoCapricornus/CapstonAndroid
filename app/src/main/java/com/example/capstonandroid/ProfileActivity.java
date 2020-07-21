package com.example.capstonandroid;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.User;


public class ProfileActivity extends AppCompatActivity {

    TextView name,mobileNumber,dob,email;
    //CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        map();

        if(getIntent().getExtras() != null) {
            User user = (User) getIntent().getSerializableExtra("user");
            if(user != null){
                name.setText(user.getUsername());
                mobileNumber.setText(user.getPhone());
                dob.setText(user.getDob());
                email.setText(user.getEmail());
            }
        }
    }

    private void map() {
        name = findViewById(R.id.name);
        mobileNumber = findViewById(R.id.mobileNumber);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        //profile = findViewById(R.id.profile);
    }
}
