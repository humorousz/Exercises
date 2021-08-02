package com.humorusz.practice.dagger.test;

import com.humorusz.practice.dagger.DaggerFragment;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {FetchStudentService.class, StudentMoudle.class})
public interface TestComponent {
  void inject(DaggerFragment fragment);
}
