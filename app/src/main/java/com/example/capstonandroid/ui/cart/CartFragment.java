package com.example.capstonandroid.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerCartAdapter;
import com.example.capstonandroid.entity.Cart;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.firebase.ProductDB;
import com.example.capstonandroid.firebaseinterface.Auxiliary;
import com.example.capstonandroid.firebaseinterface.QuantityChangeListener;

import java.util.List;

public class CartFragment extends Fragment implements QuantityChangeListener {

    private CartViewModel cartViewModel;

    private TextView total, tvLink;
    private RecyclerView recyclerView;
    private RecyclerCartAdapter recyclerCartAdapter;
    private String user_name;
    private Button btnApply, btnContinue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        cartViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> s) {

            }
        });

        final SharedPreferences sharedPref = getContext().getSharedPreferences("datalogin", Context.MODE_PRIVATE);
        user_name = sharedPref.getString("user_name", "");

        tvLink = root.findViewById(R.id.tv_link);
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliary.dialogStatus(getContext(), false, "Chức năng này chưa mở! Vui lòng chờ đến hết tết!");
            }
        });

        total = root.findViewById(R.id.tv_total);

        setTotalPrice();
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_view);

        recyclerCartAdapter = new RecyclerCartAdapter(getContext(), Auxiliary.carts, user_name, this);
        recyclerCartAdapter.setOnItemClickListener(new RecyclerCartAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
            @Override
            public void onItemLongClick(int position, View v) {
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerCartAdapter);


        btnApply = root.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliary.dialogStatus(getContext(), false, "Chức năng này chưa mở! Vui lòng chờ đến hết tết!");
            }
        });

        final ProductDB db = new ProductDB("inventory/products");
        btnContinue = root.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart userCart = Auxiliary.carts.get(checkUserPosition(user_name));
                if(userCart.getProducts().size()!=0){
                    for(int i = 0;i < userCart.getProducts().size();i++){
                        if(userCart.getQuantities().get(i).isChecked()){
                            db.updateProductByID(
                                    userCart.getProducts().get(i).getProductID(),
                                    "unitInStock",
                                    "int",
                                    String.valueOf(
                                            userCart.getProducts().get(i).getUnitInStock() -
                                                    userCart.getQuantities().get(i).getQuantity()
                                    )
                            );
                            userCart.getProducts().get(i).setUnitInStock(
                                    userCart.getProducts().get(i).getUnitInStock() -
                                            userCart.getQuantities().get(i).getQuantity()
                            );
                        }
                    }
                    if(allProductIsNotChecked()){
                        recyclerCartAdapter.notifyDataSetChanged();
                        Auxiliary.dialogStatus(getContext(), false, "Check một sản phẩm đi! Không check mua thế nào đéo được");
                    } else {
                        recyclerCartAdapter.notifyDataSetChanged();
                        Auxiliary.dialogStatus(getContext(), true, "Good good good! Cuối cùng cũng có thằng ngu nó mua");
                    }
                } else {
                    Auxiliary.dialogStatus(getContext(), false, "Đéo có sản phẩm nào đâu mà mua!");
                }
            }
        });

        return root;
    }

    public boolean allProductIsNotChecked(){
        for(int i = 0; i < Auxiliary.carts.get(checkUserPosition(user_name)).getQuantities().size(); i++){
            if(Auxiliary.carts.get(checkUserPosition(user_name)).getQuantities().get(i).isChecked()){
                return false;
            }
        }
        return true;
    }

    public int checkUserPosition(String UserName){
        for(int i = 0; i < Auxiliary.carts.size(); i++){
            if(Auxiliary.carts.get(i).getUserName().equalsIgnoreCase(UserName)){
                return i;
            }
        }
        return -1;
    }

    public void setTotalPrice(){
        double totalprice = 0;
        if(checkUserPosition(user_name)!=-1) {
            Cart cart = Auxiliary.carts.get(checkUserPosition(user_name));
            for (int i = 0; i < cart.getProducts().size(); i++) {
                if (cart.getQuantities().get(i).isChecked()) {
                    totalprice += cart.getProducts().get(i).getUnitPrice() * cart.getQuantities().get(i).getQuantity();
                }
            }
            total.setText("" + totalprice);
        } else {
            total.setText("" + 0);
        }
    }

    @Override
    public void onQuantityChange() {
        setTotalPrice();
    }
}