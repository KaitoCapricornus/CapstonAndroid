package com.example.capstonandroid.ui.product.ui.comment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CommentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Comment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}