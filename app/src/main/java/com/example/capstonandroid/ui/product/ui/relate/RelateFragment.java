package com.example.capstonandroid.ui.product.ui.relate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.capstonandroid.R;

public class RelateFragment extends Fragment {

    private RelateViewModel relateViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        relateViewModel =
                ViewModelProviders.of(this).get(RelateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_relate, container, false);

        relateViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}