package com.humorousz.networklibrary;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhangzhiquan
 * @date 2018/11/3
 */
public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager mInstance;
    private static Retrofit retrofit;
    private static volatile TianApiService request = null;

    public static NetworkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetworkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetworkManager();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BaseParametersInterceptor()).build();

        HttpUrl url = new HttpUrl.Builder().scheme("http")
                .host("api.tianapi.com")
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }

    public static TianApiService getTianRequest() {
        if (request == null) {
            synchronized (TianApiService.class) {
                request = retrofit.create(TianApiService.class);
            }
        }
        return request;
    }
}
