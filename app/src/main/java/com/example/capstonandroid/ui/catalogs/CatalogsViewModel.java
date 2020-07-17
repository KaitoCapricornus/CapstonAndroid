package com.example.capstonandroid.ui.catalogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatalogsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CatalogsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}