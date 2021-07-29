package com.humorousz.networklibrary;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zhangzhiquan
 * @date 2018/11/11
 */
public interface TianApiService {
    @GET("/social/")
    Observable<TianBaseResponse> getSocial(@Query("num") int num);
}
