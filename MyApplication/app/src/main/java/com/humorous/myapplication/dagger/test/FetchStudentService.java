package com.humorous.myapplication.dagger.test;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import dagger.Module;

@Module
public class FetchStudentService {
  @Inject
  public FetchStudentService() {
  }

  public Student getStudentByName(String name) {
    return new Student(name);
  }

  public void startActivity(@Nullable Activity activity, String uid) {
    if (activity == null) {
      return;
    }
    Intent intent = new Intent();
    intent.setData(Uri.parse("kwai://live/play/~" + uid));
    activity.startActivity(intent);
  }
}