package com.example.capstonandroid;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassActivity extends AppCompatActivity {
    EditText email,newpass,confirmnewpass;
    Button btn_changepass;
    boolean checkEmail = false;
    processDialog process = new processDialog(this);
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/identity/Customer");
    private ValueEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_forgot);
        map();

        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString();
                final String Newpass = newpass.getText().toString();
                final String ConfirmNewPass = confirmnewpass.getText().toString();
                final AlertDialog alertDialog = new AlertDialog.Builder(ForgotPassActivity.this).create();
                process.start();
                if(!TextUtils.isEmpty(Email)  && !TextUtils.isEmpty(Newpass) && !TextUtils.isEmpty(ConfirmNewPass)) {

                    mListener=mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                try {
                                    if (ds.child("email").getValue(String.class).equalsIgnoreCase(Email)) {
                                        checkEmail = true;
                                        if (Newpass.equals(ConfirmNewPass)) {
                                            mDatabase.child(ds.getKey()).child("pass").setValue(Newpass);
                                            new AlertDialog.Builder(ForgotPassActivity.this)
                                                    .setTitle("Notification")
                                                    .setMessage("Change successfully! Please return Login !")
                                                    .setPositiveButton(android.R.string.yes, null)
                                                    .show();
                                            stopListener();
                                            process.end();
                                            break;
                                        } else {
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setMessage("Password not match!");
                                            alertDialog.show();
                                            stopListener();
                                            process.end();
                                            return;
                                        }
                                    }

                                } catch (Exception e) {
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("Server is Disable");
                                    alertDialog.show();
                                    stopListener();
                                    process.end();
                                    return;
                                }
                            }

                                if (!checkEmail) {
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("Email not Exist");
                                    process.end();
                                    stopListener();
                                    alertDialog.show();
                                }


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                } else {
                    alertDialog.setTitle("Notificaton");
                    alertDialog.setMessage("Please fill all Field!");
                    alertDialog.show();
                    process.end();
                    return;
                }
            }
        });

    }

    private void map(){
        email = findViewById(R.id.email);
        newpass = findViewById(R.id.newpass);
        confirmnewpass = findViewById(R.id.confirmnewpass);
        btn_changepass = findViewById(R.id.btn_changepass);
    }

    public void stopListener(){
        mDatabase.removeEventListener(mListener);
    }

}
