package com.humorous.myapplication.rxjava.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.view.BaseFragment;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangzhiquan on 2018/2/1.
 */

public class RxTestFragment extends BaseFragment {
    private static final String TAG = "RxTestFragment";
    private TextView mText;
    private Button mBtn;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_rxjava,container,false);
    }

    @Override
    public void initView(View root) {
        mText = root.findViewById(R.id.text_rx);
        mBtn = root.findViewById(R.id.btn_map);
        mBtn.setOnClickListener((v)->{
            testMap(1);
        });
    }


    private void testMap(int inputNum){
        Observable.<Integer>create((e)->{
            e.onNext(inputNum);
            e.onComplete();
        }).map((input)->{
           String s = "this is num"+input;
           return  s;
        }).subscribe(new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d(TAG,"onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Logger.d(TAG,value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Logger.d(TAG,"onComplete");
            }
        });
    }
}
