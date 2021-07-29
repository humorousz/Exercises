package com.humorousz.networklibrary;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhangzhiquan
 * @date 2018/11/11
 */
public class BaseParametersInterceptor implements Interceptor {
    private static final String KEY = "5689ede0a2e303e045f8ada57b9239cb";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl builder = request.url().newBuilder()
                .addQueryParameter("key",KEY).build();
        request=request.newBuilder()
                .url(builder)
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
