package com.humorous.myapplication.rxjava.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.networklibrary.NetworkManager;
import com.humorousz.uiutils.view.BaseFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhiquan on 2018/2/1.
 */

public class RxTestFragment extends BaseFragment {
    private static final String TAG = "RxTestFragment";
    private TextView mText;
    private Button mBtn;
    Disposable disposable;
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
        getSocial();
    }

    private void getSocial(){
        Disposable disposable =  NetworkManager.getTianRequest().getSocial(10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(mapper -> mapper.itemList)
                .subscribe( list -> {
            Logger.d(TAG,"tianBaseResponse : successful size:"+list.size());
        },error->{
            Logger.d(TAG,"tianBaseResponse : error : "+error.toString());
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable != null){
            disposable.dispose();
        }
    }

    Observable<Integer> mObservable;
    private void testMap(int inputNum){
        Logger.d(TAG,"testMap");
        if(mObservable == null){
            mObservable = Observable.create((e)->{
                Logger.d(TAG,"onNext");
                e.onNext(inputNum);
                e.onComplete();
            });
        }

         mObservable.delaySubscription(3,TimeUnit.SECONDS).map((input)->{
           String s = "this is num"+input;
           return  s;
        }).subscribe(new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d(TAG,"onSubscribe");
                disposable = d;
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
