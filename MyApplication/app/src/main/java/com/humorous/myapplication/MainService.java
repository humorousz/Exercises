package com.humorous.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.humorousz.commonutils.log.Logger;

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
        public void testLog() throws RemoteException {
            Logger.d("MRService","Thread:"+Thread.currentThread());
            Logger.d("MRService","--begin--");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.d("MRService","Thread:"+Thread.currentThread());
            Logger.d("MRService","--end--");
        }
    }
}
