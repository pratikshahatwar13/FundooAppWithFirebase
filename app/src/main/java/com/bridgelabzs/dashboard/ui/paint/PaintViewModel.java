package com.bridgelabzs.dashboard.ui.paint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaintViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PaintViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is paint fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

