package com.example.capstonandroid.ui.product.ui.judge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JudgeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JudgeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}