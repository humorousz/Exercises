package com.humorousz.uiutils.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.R;
import com.humorousz.uiutils.helper.StatusBarUtil;

/**
 * -- abstract Class --
 * BaseFragment
 * 所有Fragment的基类，所有使用的fragment都需要从此类继承，方便对生命周期等内容的控制
 */


public abstract class BaseFragment extends Fragment {
    //标志位，标志fragment已经初始化完成
    public  boolean prepared;
    //标志位，是否执行了init
    protected boolean init = false;

    protected boolean firstVisible = true;
    protected boolean firstInvisible = true;
    protected boolean destoryView = false;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        printLog("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        printLog("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        printLog("onCreateView");
        View view = createView(inflater,container,savedInstanceState);
        initView(view);
        if(view.findViewById(R.id.statusbarutil_sub_padding_view) != null){
            StatusBarUtil.setTranslucentForRootPadding(getActivity(),0,view.findViewById(R.id.statusbarutil_sub_padding_view));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        printLog("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        printLog("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        printLog("onResume");
        super.onResume();
        /**
         * 如果不是第一次可见但是还没有init，说明是第一个fragment，
         * 在firstVisible时initView没有执行因为先执行了setUserVisibleHint，
         * 需要在onResume时在调用一次onFirstVisible
         */
        if(!firstVisible && !init){
            onFirstVisible();
        }
    }

    @Override
    public void onPause() {
        printLog("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        printLog("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        printLog("onDestroyView");
        super.onDestroyView();
        destoryView = true;
    }

    @Override
    public void onDestroy() {
        printLog("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        printLog("onDetach");
        super.onDetach();
    }

    protected void printLog(String methodName){
        if(logLife()){
            Logger.d(getLogTitle(),methodName);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(firstVisible){
                firstVisible = false;
                initPrepare();
            }else {
                onVisible();
            }
        }else {
            if(firstInvisible){
                firstInvisible = false;
                onFirstInvisible();
            }else {
                onInVisible();
            }
        }
    }

    public synchronized void initPrepare(){
        if(prepared){
            init = true;
            onFirstVisible();
        }
    }

    /**
     * 设置初始化完成标志
     * @param prepared
     */
    protected void setPrepared(boolean prepared){
        this.prepared = prepared;
    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void initView(View root);


    public  String getLogTitle(){
        return null;
    }

    public String getTitle() {
        return null;
    }

    protected boolean logLife(){
        return false;
    }

    protected void onVisible(){

    }

    protected void onInVisible(){

    }

    protected void onFirstVisible(){

    }

    protected void onFirstInvisible(){

    }


}
