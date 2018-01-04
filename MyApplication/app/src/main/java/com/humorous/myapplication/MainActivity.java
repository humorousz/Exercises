package com.humorous.myapplication;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.humorous.myapplication.config.api.API;
import com.humorous.myapplication.home.HomeFragment;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.StatusBarCompat;
import com.humorousz.uiutils.view.BaseFragment;

public class MainActivity extends FragmentActivity {
    //    FrameLayout mContainer;
    private static final String TAG = "MainActivity";
    FrameLayout mContainer;
    BaseFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this);
        mFragment = HomeFragment.newInstance(API.SECOND_MENU.MAIN);
        mContainer = (FrameLayout) findViewById(R.id.container);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.container,mFragment);
        tr.commit();
        Logger.d(TAG,Thread.currentThread().hashCode());
        testFunc();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface binder =  IMyAidlInterface.Stub.asInterface(service);
//            for(int i = 0 ;i < 5 ; i++){
//                Thread thread = new Thread(Thread.currentThread().getThreadGroup(),()->{
//                    try {
//                        binder.testLog();
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                },"Thread"+i);
//                thread.start();
//            }
            try {
                binder.testLog();
                binder.testLog();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void testFunc(){
        Intent intent = new Intent(this,MainService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.d(TAG,"onRestore");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Logger.d(TAG,"onSave");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d(TAG,"onRestart");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Logger.d(TAG,"onRestoreInstanceState PersistableBundle");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG,"onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"onDestroy");
    }
}
