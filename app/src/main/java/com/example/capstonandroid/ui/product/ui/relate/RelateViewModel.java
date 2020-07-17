package com.example.capstonandroid.ui.product.ui.relate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RelateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RelateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}