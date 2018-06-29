package com.humorous.myapplication;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.humorous.myapplication.config.api.Api;
import com.humorous.myapplication.home.HomeFragment;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.view.ImmerseActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

/**
 * @author zhangzhiquan
 */
public class MainActivity extends ImmerseActivity {
    private static final String TAG = "MainActivity";
    private static int REQUEST_CODE = 1001;
    private FrameLayout mContainer;
    private BaseFragment mFragment;
    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        mFragment = HomeFragment.newInstance(Api.SECOND_MENU.MAIN);
        mContainer = findViewById(R.id.container);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.container,mFragment);
        tr.commit();
        Logger.d(TAG,Thread.currentThread().hashCode());
        testFunc();
    }

    private void initToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        TextView title = mToolBar.findViewById(R.id.title);
        title.setText(R.string.main_title);
        title.setSelected(true);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mToolBar.findViewById(R.id.btn_sao_yi_sao).setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            ZxingConfig config = new ZxingConfig();
            //是否播放扫描声音 默认为true
            config.setPlayBeep(true);
            //是否震动  默认为true
            config.setShake(true);
            //是否扫描条形码 默认为true
            config.setDecodeBarCode(false);
            //是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            config.setFullScreenScan(true);
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface binder =  IMyAidlInterface.Stub.asInterface(service);
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
//        Intent intent = new Intent(this,MainService.class);
//        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    String result = bundle.getString(Constant.CODED_CONTENT);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (requestCode == RESULT_CANCELED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
