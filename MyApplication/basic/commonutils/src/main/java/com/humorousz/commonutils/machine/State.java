package com.humorousz.commonutils.machine;

import android.os.Message;

public class State implements IState {

  /**
   * Constructor
   */
  protected State() {
  }

  @Override
  public void enter() {
  }

  @Override
  public void exit() {
  }

  @Override
  public boolean processMessage(Message msg) {
    return false;
  }

  /**
   * Name of State for debugging purposes.
   * <p>
   * This default implementation returns the class name, returning
   * the instance name would better in cases where a State class
   * is used for multiple states. But normally there is one class per
   * state and the class name is sufficient and easy to get. You may
   * want to provide a setName or some other mechanism for setting
   * another name if the class name is not appropriate.
   */
  @Override
  public String getName() {
    String name = getClass().getName();
    int lastDollar = name.lastIndexOf('$');
    return name.substring(lastDollar + 1);
  }
}
