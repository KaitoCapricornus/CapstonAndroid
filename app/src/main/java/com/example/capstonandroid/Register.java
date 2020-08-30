package com.example.capstonandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonandroid.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Register extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ImageView user_image;
    EditText username,email,phone,pass,confirmpass,txtDob;
    CheckBox agree;
    Button register, btnDob;
    boolean checkEmail = false;
    processDialog processDialog = new processDialog(this);
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/identity/Customer");
    private ValueEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        map();

        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDialog.start();
                final AlertDialog alertDialog= new AlertDialog.Builder(Register.this).create();
                String UserName = username.getText().toString();
                final String Email = email.getText().toString();
                String Phone = phone.getText().toString();
                final String Pass = pass.getText().toString();
                String Confirmpass = confirmpass.getText().toString();

                if(!TextUtils.isEmpty(UserName) && !TextUtils.isEmpty(Email) &&
                        !TextUtils.isEmpty(Confirmpass) && !TextUtils.isEmpty(Phone) && !TextUtils.isEmpty(Pass)) {
                    if (!Pass.equals(Confirmpass)) {
                        processDialog.end();
                        alertDialog.setTitle("Warning!");
                        alertDialog.setMessage("Password not match!");
                        alertDialog.show();
                    } else if (!agree.isChecked()) {
                        processDialog.end();
                        alertDialog.setTitle("Warning!");
                        alertDialog.setMessage("Please agree our policy");
                        alertDialog.show();
                    } else {
                        final User user = new User(UserName, Pass, Email, Phone, "25/07/1999");
                        mListener=mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    try {
                                        if (ds.child("email").getValue(String.class).equals(Email)) {
                                            new AlertDialog.Builder(Register.this)
                                                    .setTitle("Alert")
                                                    .setMessage("This email has already existed")
                                                    .setPositiveButton(android.R.string.yes, null)
                                                    .setNegativeButton(android.R.string.no, null)
                                                    .show();
                                            stopListener();
                                            checkEmail = true;
                                            processDialog.end();
                                            break;
                                        } else {
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Server is Disable");
                                        processDialog.end();
                                        alertDialog.show();
                                        stopListener();
                                        return;
                                    }
                                }
                                if(!checkEmail){
                                    String userId = mDatabase.push().getKey();
                                    mDatabase.child(userId).setValue(user);
                                    processDialog.end();
                                    new AlertDialog.Builder(Register.this)
                                            .setTitle("Notification")
                                            .setMessage("Register successfully! Please return Login !")
                                            .setPositiveButton(android.R.string.yes,null)
                                            .show();
                                    stopListener();
                                    processDialog.end();
                                    Intent intent = new Intent(Register.this,LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                } else {
                    alertDialog.setTitle("Notificaton");
                    alertDialog.setMessage("Please fill all Field!");
                    alertDialog.show();
                    processDialog.end();
                    return;
                }

            }
        });

    }

    public void stopListener(){
        mDatabase.removeEventListener(mListener);
    }

    private void map() {
        btnDob=findViewById(R.id.btnDob);
        txtDob=findViewById(R.id.txtDob);
        user_image = findViewById(R.id.user_image);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        confirmpass = findViewById(R.id.confirmpass);
        agree = findViewById(R.id.agree);
        register = findViewById(R.id.register);
    }

    private void showDatePickerDialog(){
        DatePickerDialog d=new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        d.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date=dayOfMonth+"/"+(month+1)+"/"+year;
        txtDob.setText(date);
    }
}


