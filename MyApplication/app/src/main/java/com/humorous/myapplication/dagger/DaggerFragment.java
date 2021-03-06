package com.humorous.myapplication.dagger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorous.myapplication.dagger.test.DaggerTestComponent;
import com.humorous.myapplication.dagger.test.FetchStudentService;
import com.humorous.myapplication.dagger.test.Student;
import com.humorous.myapplication.dagger.test.StudentType;
import com.humorous.myapplication.dagger.test.Teacher;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.view.BaseFragment;

import javax.inject.Inject;

public class DaggerFragment extends BaseFragment {
  @Inject
  Teacher mTeacher;
  @Inject
  Student mStudent;
  @Inject
  Student mStudentA;
  @StudentType
  @Inject
  Student mStudentType1;
  @StudentType(2)
  @Inject
  Student mStudentType2;
  @Inject
  FetchStudentService mSerivice;
  private View mButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerTestComponent.builder().build().inject(this);
  }

  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.dagger_fragment, container, false);
  }

  @Override
  public void initView(View root) {
    Logger.d(mSerivice.getStudentByName("aaaaa").getName());
    mButton = root.findViewById(R.id.click_button);
    mButton.setOnClickListener(v -> {
      Log.d("MRZ", mStudent.toString());
      Log.d("MRZ", mStudentA.toString());
      Log.d("MRZ", mStudentType1.toString());
      Log.d("MRZ", mStudentType2.toString());
    });
  }
}
