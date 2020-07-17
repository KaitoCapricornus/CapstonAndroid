package com.example.capstonandroid.ui.product.ui.judge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.capstonandroid.R;


public class JudgeFragment extends Fragment {

    private JudgeViewModel judgeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        judgeViewModel =
                ViewModelProviders.of(this).get(JudgeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_judge, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        judgeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}