package com.example.btntest.ui.constructor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConstructorViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ConstructorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Тут конструктор");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
