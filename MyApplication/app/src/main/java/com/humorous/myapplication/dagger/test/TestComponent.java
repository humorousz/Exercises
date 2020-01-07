package com.humorous.myapplication.dagger.test;

import com.humorous.myapplication.dagger.DaggerFragment;

import dagger.Component;


@Component(modules = {FetchStudentService.class, StudentMoudle.class})
public interface TestComponent {
  void inject(DaggerFragment fragment);
}
