package com.bridgelabzs.dashboard.ui.mic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MicViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mic fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}