package com.example.capstonandroid.ui.order.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.capstonandroid.R;
import com.example.capstonandroid.entity.Address;
import com.example.capstonandroid.firebaseinterface.MyCallbackInterface;

import java.util.List;

public class OrderActivityThucFragment extends Fragment {

    private MainViewModel orderViewModel;
    private RadioGroup addressRG;
    private RadioButton rb_creditCard;
    private RadioButton rb_cod;
    private RadioButton rb_firstCard;
    private RadioButton rb_secondCard;
    private RadioButton rb_newCard;
    private RadioGroup rg_creditOrCod;
    private RadioGroup rg_creditCardNumber;
    private RelativeLayout addAddressBoundView;
    private RelativeLayout addCreditCardView;
    private EditText address_edt;
    private Button confirm_button;
    private Button rd_hanoi;
    private String address;
    private String paymentMethod;
    private String creditCardNumber;
    private static OrderActivityThucFragment instance;

    public static OrderActivityThucFragment newInstance() {
        if (instance == null) return new OrderActivityThucFragment();
        else return instance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(MainViewModel.class);
        orderViewModel.callback = new MyCallbackInterface<List<Address>>() {
            @Override
            public void onCallBack(List<Address> value) {
                rd_hanoi.setText(orderViewModel.getData().getValue().get(0).address);
            }
        };
        View root = inflater.inflate(R.layout.fragment_order_dung, container, false);
        rd_hanoi = root.findViewById(R.id.rd_hanoi);
        rb_creditCard = root.findViewById(R.id.rb_creditCard);
        rb_cod = root.findViewById(R.id.rb_cod);
        rb_firstCard = root.findViewById(R.id.rb_firstCard);
        rb_secondCard = root.findViewById(R.id.rb_secondCard);
        rb_newCard = root.findViewById(R.id.rb_newCard);
        addressRG = root.findViewById(R.id.rg_address);
        addAddressBoundView = root.findViewById(R.id.addAddressBoundView);
        address_edt = root.findViewById(R.id.address_edt);
        address_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address = address_edt.getText().toString();
            }
        });
        addressRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (addressRG.getCheckedRadioButtonId()) {
                    case R.id.rd_hanoi:
                        address = "Hà Nội";
                        break;
                    case R.id.rd_ninhbinh:
                        address = "Ninh Bình";
                        break;
                    case R.id.rd_addAddress:
                        if (addAddressBoundView.getVisibility() == View.VISIBLE) {

                        } else {
                            addAddressBoundView.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });
        rg_creditOrCod = root.findViewById(R.id.rg_creditOrCod);
        rg_creditOrCod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group == rg_creditOrCod) {
                    switch (rg_creditOrCod.getCheckedRadioButtonId()) {
                        case R.id.rb_creditCard:
                            // if (!rb_cod.isChecked()) {
                            paymentMethod = "Credit card";
//                            Toast.makeText(getContext(), "credit card", Toast.LENGTH_LONG).show();
                            rb_cod.setChecked(false);
                            // }
                            break;
                        case R.id.rb_cod:
                            // if (!rb_creditCard.isChecked()) {
                            rb_creditCard.setChecked(false);
                            rg_creditCardNumber.clearCheck();
//                            rb_newCard.setChecked(false);
//                            rb_firstCard.setChecked(false);
//                            rb_secondCard.setChecked(false);
                            paymentMethod = "COD";
                            creditCardNumber = "";
//                            Toast.makeText(getContext(), "cod", Toast.LENGTH_LONG).show();
                            // }
                            break;
                    }
                }

            }
        });
        rg_creditCardNumber = root.findViewById(R.id.rg_creditCardNumber);
        rg_creditCardNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group == rg_creditCardNumber) {
                    switch (rg_creditCardNumber.getCheckedRadioButtonId()) {
                        case R.id.rb_firstCard:
                            if (rb_firstCard.isChecked()) {
                                creditCardNumber = "xxx xxx xxx 357";
                                rb_creditCard.setChecked(true);
//                                Toast.makeText(getContext(), "rb_firstCard credit card", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case R.id.rb_secondCard:
                            if (rb_secondCard.isChecked()) {
                                creditCardNumber = "xxx xxx xxx 149";
                                rb_creditCard.setChecked(true);
//                                Toast.makeText(getContext(), "rb_secondCard credit card", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case R.id.rb_newCard:
                            if (rb_newCard.isChecked()) {
                                addCreditCardView.setVisibility(View.VISIBLE);
                                creditCardNumber = "";
                                rb_creditCard.setChecked(true);
//                                Toast.makeText(getContext(), "new credit card", Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
                }
            }
        });

        addCreditCardView = root.findViewById(R.id.addCreditCardView);
        confirm_button = root.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Dữ liệu đơn hàng đã được lưu lại", Toast.LENGTH_LONG).show();
            }
        });

        // rd_hanoi.setText(orderViewModel.getData().getValue().get(0).address);
        return root;
    }

}