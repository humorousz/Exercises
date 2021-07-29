package com.humorusz.practice.dagger.test;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module()
public class StudentMoudle {
  @StudentType(1)
  @Provides
  public Student provideStudentA() {
    return new Student("ZHANG");
  }

  @StudentType(2)
  @Provides
  public Student provideStudentB() {
    return new Student("WANG");
  }

  @Singleton
  @Provides
  public Student provideNomalStudent() {
    return new Student("Nomal");
  }
}
