package com.humorous.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.commonutils.machine.State;
import com.humorousz.commonutils.machine.StateMachine;

public class MainService extends Service {
  public MainService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return new TestBinder();
  }


  class TestBinder extends IMyAidlInterface.Stub {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void testLog() {
      testMachine();
    }

    private void testMachine() {
      StateMachine machine = MyStateMachine.create();
      machine.sendMessage(2);
    }
  }

  static class MyStateMachine extends StateMachine {
    State mP1 = new P1();
    State mP2 = new P2();

    protected MyStateMachine(String name) {
      super(name);
      addState(mP1);
      addState(mP2);
    }

    public static MyStateMachine create() {
      Logger.d("MRZ", "create E");
      MyStateMachine stateMachine = new MyStateMachine("MyStateMachine");
      stateMachine.start();
      Logger.d("MRZ", "create X");
      return stateMachine;
    }

    class P1 extends State {
      @Override
      public void enter() {
        super.enter();
        Logger.d("MRZ", "P1 enter");
      }

      @Override
      public void exit() {
        super.exit();
        Logger.d("MRZ", "P1 exit");
      }

      @Override
      public boolean processMessage(Message msg) {
        Logger.d("MRZ", "P1 processMessage:" + msg.what);
        switch (msg.what) {
          case 1:
            sendMessage(obtainMessage(2));
            transitionTo(mP2);
            return HANDLED;
          default:
            return NOT_HANDLED;
        }
      }
    }

    class P2 extends State {
      @Override
      public void enter() {
        super.enter();
        Logger.d("MRZ", "P2 enter");
      }

      @Override
      public void exit() {
        super.exit();
        Logger.d("MRZ", "P2 exit");
      }

      @Override
      public boolean processMessage(Message msg) {
        Logger.d("MRZ", "P2 processMessage:" + msg.what);
        switch (msg.what) {
          case 2:
            return HANDLED;
          default:
            return NOT_HANDLED;
        }
      }
    }
  }
}
