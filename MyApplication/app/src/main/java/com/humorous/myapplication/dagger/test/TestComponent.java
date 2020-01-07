package com.humorous.myapplication.dagger.test;

import com.humorous.myapplication.dagger.DaggerFragment;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {FetchStudentService.class, StudentMoudle.class})
public interface TestComponent {
  void inject(DaggerFragment fragment);
}
