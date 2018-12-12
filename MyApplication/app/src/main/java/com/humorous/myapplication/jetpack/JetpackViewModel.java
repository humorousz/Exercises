package com.humorous.myapplication.jetpack;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class JetpackViewModel extends ViewModel {
  MutableLiveData<JetLiveData> mLiveData;

  public LiveData<JetLiveData> getLiveData() {
    if (mLiveData == null) {
      mLiveData = new MutableLiveData<>();
    }
    return mLiveData;
  }

  public void getCurrentTime() {
    JetLiveData data = new JetLiveData();
    data.time = String.valueOf(System.currentTimeMillis());
    mLiveData.setValue(data);
  }
}
