package com.example.capstonandroid.firebaseinterface;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstonandroid.R;
import com.example.capstonandroid.entity.Cart;

import java.util.ArrayList;

public class Auxiliary {
    public static ArrayList<Cart> carts;
    public static Cart cart;

    public static void dialogStatus(Context context, boolean isSuccess,String statusComment){
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.status_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialogBuilder.setView(promptsView);

        ImageView imgStatus = promptsView.findViewById(R.id.img_status);
        TextView tvStatus = promptsView.findViewById(R.id.tv_status);

        if(isSuccess){
            Glide.with(context).load(R.drawable.successful_logo).into(imgStatus);
        } else {
            Glide.with(context).load(R.drawable.unsuccessful_logo).into(imgStatus);
        }

        tvStatus.setText(statusComment);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
