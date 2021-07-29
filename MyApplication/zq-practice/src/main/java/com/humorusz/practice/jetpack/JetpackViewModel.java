package com.humorusz.practice.jetpack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
