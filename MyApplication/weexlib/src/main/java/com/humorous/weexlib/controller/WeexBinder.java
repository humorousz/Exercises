package com.humorous.weexlib.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

/**
 * @author zhangzhiquan
 * on 2018/5/27
 */
public class WeexBinder implements IWXRenderListener {
    private String mUrl;
    private WXSDKInstance mInstance;
    private Context mContext;
    private ViewGroup mContainer;
    public WeexBinder(Builder builder){
        mUrl = builder.url;
        mContext = builder.context;
        mContainer = builder.container;
        mInstance = new WXSDKInstance(mContext);
        mInstance.registerRenderListener(this);
    }

    public void render(){

        mInstance.render("WXSample", WXFileUtils.loadFileOrAsset("hello.js", mContext), null, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
    }

    public static class Builder{
        private String url;
        private Context context;
        private ViewGroup container;
        public Builder(){

        }

        public Builder setContext(Context context){
            this.context = context;
            return this;
        }

        public Builder setContainer(ViewGroup container){
            this.container = container;
            return this;
        }

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }

        public WeexBinder build(){
            return new WeexBinder(this);
        }
    }


    public void onResume(){
        if(mInstance != null){
            mInstance.onActivityResume();
        }
    }

    public void onPause(){
        if(mInstance != null){
            mInstance.onActivityPause();
        }
    }

    public void onStop(){
        if(mInstance != null){
            mInstance.onActivityStop();
        }
    }

    public void onDestroy(){
        if(mInstance != null){
            mInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if(mContainer != null){
            mContainer.addView(view);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }
}
