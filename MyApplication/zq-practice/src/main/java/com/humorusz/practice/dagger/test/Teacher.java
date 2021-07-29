package com.humorusz.practice.dagger.test;


import com.humorousz.commonutils.log.Logger;

import javax.inject.Inject;

import dagger.Module;

@Module
public class Teacher {
  @Inject
  public Teacher() {
  }

  public void test() {
    Logger.d("MRZ", "Test");
  }
}
