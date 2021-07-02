package com.humorusz.practice.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.commonutils.machine.State;
import com.humorousz.commonutils.machine.StateMachine;
import com.humorousz.practice.aidl.IMyAidlInterface;

public class MainService extends Service {
  static final int CLICK = 1;
  static final int LONG_CLICK = 2;
  static final int RESET_FINISH = 3;
  static final int CONNECT_SUCCESS = 4;

  public MainService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return new TestBinder();
  }

  class TestBinder extends IMyAidlInterface.Stub {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
        String aString) throws RemoteException {

    }

    @Override
    public void testLog() {
      testMachine();
    }

    private void testMachine() {
      StateMachine machine = MyStateMachine.create();
      machine.sendMessage(CLICK);
      machine.sendMessage(CLICK);
      machine.sendMessage(LONG_CLICK);
      machine.sendMessageDelayed(LONG_CLICK,2000);
    }
  }

  /**
   * powerOn  (click) -> powerOff
   * powerOff (click) -> powerOn
   * powerOn (longClick) -> connect
   * powerOff (longClick) -> reset
   */
  static class MyStateMachine extends StateMachine {
    State mPowerOnState;
    State mPowerOffState;
    State mResetState;
    State mConnectState;
    State mParentState = new DefaultState();

    protected MyStateMachine(String name) {
      super(name);
      mPowerOffState = new PowerOffState();
      mPowerOnState = new PowerOnState();
      mConnectState = new ConnectState();
      mResetState = new ResetState();
      mParentState = new DefaultState();
      addState(mPowerOffState, mParentState);
      addState(mPowerOnState, mParentState);
      addState(mConnectState, mParentState);
      addState(mResetState, mParentState);
      setInitialState(mPowerOffState);
    }

    @Override protected void unhandledMessage(Message msg) {
      Logger.d("MRZ", "Busy now can not handle message,please retry after!");
    }

    public static MyStateMachine create() {
      Logger.d("MRZ", "create E");
      MyStateMachine stateMachine = new MyStateMachine("MyStateMachine");
      stateMachine.start();
      Logger.d("MRZ", "create X");
      return stateMachine;
    }

    class DefaultState extends State {
      @Override public boolean processMessage(Message msg) {
        return super.processMessage(msg);
      }
    }

    class PowerOffState extends State {
      @Override public void enter() {
        super.enter();
        Logger.d("MRZ", "now power off");
      }

      @Override public boolean processMessage(Message msg) {
        int what = msg.what;
        switch (what) {
          case CLICK:
            transitionTo(mPowerOnState);
            return HANDLED;
          case LONG_CLICK:
            transitionTo(mResetState);
            return HANDLED;
          default:
            break;
        }
        return NOT_HANDLED;
      }
    }

    class PowerOnState extends State {
      @Override public void enter() {
        Logger.d("MRZ", "now power On");
      }

      @Override public boolean processMessage(Message msg) {
        int what = msg.what;
        switch (what) {
          case CLICK:
            transitionTo(mPowerOffState);
            return HANDLED;
          case LONG_CLICK:
            transitionTo(mConnectState);
            return HANDLED;
          default:
            break;
        }
        return NOT_HANDLED;
      }
    }

    class ResetState extends State {
      @Override public void enter() {
        super.enter();
        Logger.d("MRZ", "enter reset start....");
        sendMessageDelayed(RESET_FINISH, 1000);
      }

      @Override public void exit() {
        super.exit();
        Logger.d("MRZ", "exit reset finish....");
      }

      @Override public boolean processMessage(Message msg) {
        if (msg.what == RESET_FINISH) {
          transitionTo(mPowerOffState);
          return HANDLED;
        }
        return super.processMessage(msg);
      }
    }

    class ConnectState extends State {
      @Override public void enter() {
        super.enter();
        Logger.d("MRZ", "enter connect start....");
        sendMessageDelayed(CONNECT_SUCCESS, 1000);
      }

      @Override public void exit() {
        super.exit();
        Logger.d("MRZ", "exit connect finish....");
      }

      @Override public boolean processMessage(Message msg) {
        if (msg.what == CONNECT_SUCCESS) {
          transitionTo(mPowerOnState);
          return HANDLED;
        }
        return super.processMessage(msg);
      }
    }
  }
}
