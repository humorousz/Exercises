package com.humorusz.practice.dagger.car;

import dagger.Component;

/**
 * @author zhangzhiquan
 * @date 2019-12-29
 */
@Component
public interface CarCommponet {
  void inject(Car car);
}
