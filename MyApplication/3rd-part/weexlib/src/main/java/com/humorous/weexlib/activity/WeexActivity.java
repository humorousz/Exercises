package com.humorous.weexlib.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.humorous.weexlib.R;
import com.humorous.weexlib.controller.WeexBinder;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.view.ImmerseActivity;

/**
 * @author zhangzhiquan
 * on 2018/05/19
 */
public class WeexActivity extends ImmerseActivity {
    private static final String TAG = "WeexActivity";
    private WeexBinder mBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex);
        initParams();
    }

    @Override
    public int getStatusBarAlpha() {
        return 0x99;
    }

    private void initParams(){
        if(getIntent() == null){
            finish();
        }
        Uri data = getIntent().getData();
        String url = data.getQueryParameter("jsBundle");
        initWeex(url);
    }

    private void initWeex(String jsBundle){
        if (TextUtils.isEmpty(jsBundle)) {
            ToastUtil.showToast(this,"错误:jsBundle 为空");
            finish();
            return;
        }
        Logger.d(TAG,"jsBundle:"+jsBundle);
        WeexBinder.Builder builder = new WeexBinder.Builder();
        builder.setContext(this)
                .setContainer(findViewById(R.id.container))
                .setUrl(jsBundle);
        mBinder = builder.build();
        mBinder.render();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinder.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mBinder.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBinder.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder.onDestroy();
    }
}
