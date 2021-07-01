package com.humorousz.commonutils.service;

import android.app.Application;

import com.humorousz.commonutils.log.Logger;

import java.util.HashMap;

/**
 * Created by zhangzhiquan on 2017/5/4.
 */

public class CommonService {

    private static final String TAG = "CommonService";
    public static  String SERVICE_APPCONTEXT = "ApplicationContext";
    private static CommonService mInstance;
    private static boolean hasInit = false;
    private static HashMap<String,BaseService> mService;
    private CommonService(){

    }

    public static CommonService getService(){
        if(mInstance == null){
            synchronized (CommonService.class){
                if(mInstance == null){
                    mInstance = new CommonService();
                }
            }
        }
        return mInstance;
    }


    public  void init(Application application){
        if(hasInit){
            Logger.e(TAG,"CommonService has been initialized");
            return;
        }
        mService = new HashMap<>();
        initAppContext(application);
        hasInit = true;
    }

    private static void initAppContext(Application application){
        AppContextService AppcontextService = new AppContextService(application);
        mService.put(SERVICE_APPCONTEXT,AppcontextService);
    }


    public static Application getApplication(){
        return ((AppContextService)getService(SERVICE_APPCONTEXT)).mApplication;
    }


    public static BaseService getService(String key){
        if(!hasInit){
            Logger.e(TAG,"commonService is not Initialization");
            return null;
        }
        if(mService.containsKey(key)){
            return mService.get(key);
        }
        Logger.e(TAG,"don't have the key:"+key);
        return null;
    }


    /**
     * Service Class
     */
    private abstract  static class BaseService{

    }

    private static class AppContextService extends  BaseService{
        Application mApplication;
        public AppContextService(Application application){
            this.mApplication = application;
        }
    }
}
