package com.humorous.myapplication.dagger.test;


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
}
