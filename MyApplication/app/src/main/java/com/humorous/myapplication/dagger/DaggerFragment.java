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
  @StudentType(2)
  @Inject
  Student mStudent;

  @StudentType
  @Inject
  Student mStudentA;
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
    mTeacher.test();
    Logger.d(mSerivice.getStudentByName("aaaaa").getName());
    mButton = root.findViewById(R.id.click_button);
    String uid = "171174481";
    mButton.setOnClickListener(v -> {
      Log.d("MRZ", mStudent.getName());
      Log.d("MRZ", mStudentA.getName());
    });
  }
}
