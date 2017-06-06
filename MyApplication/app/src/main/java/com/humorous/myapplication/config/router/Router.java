package com.humorous.myapplication.config.router;

import android.content.Context;
import android.content.Intent;

import com.humorous.myapplication.config.api.TestProtocol;
import com.humorous.myapplication.config.factory.TestFragmentFactory;
import com.humorous.myapplication.container.ContainerActivity;

import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class Router {
    private static final String TAG = "Router";
    public static void jumpTo(Context context,String link){
        switch (link){
            case TestProtocol.ANT_TEST:
                goAnt(context);
                break;
            case TestProtocol.COORDINATOR_TEST:
                goCoordinator(context);
                break;
            case TestProtocol.TOPIC_RECYCLER_TEST:
                goTopicRecycler(context);
                break;
        }
    }



    private static void goAnt(Context context){
        Logger.d(TAG,"go to ant");
        startActivity(context,true, TestFragmentFactory.TYPE.ANT);

    }

    private static void goCoordinator(Context context){
        Logger.d(TAG,"go to coordinator");
        startActivity(context,true, TestFragmentFactory.TYPE.COORDINATOR);

    }

    private static void goTopicRecycler(Context context){
        Logger.d(TAG,"go to topicRecycler");
        startActivity(context,true, TestFragmentFactory.TYPE.TOPIC_RECYCLER);
    }

    private static void startActivity(Context context,boolean hasTitle,TestFragmentFactory.TYPE type){
        if(context == null){
            Logger.e(TAG,"context must not be null");
            return;
        }
        Intent intent =  new Intent(context, ContainerActivity.class);
        intent.putExtra(ContainerActivity.HAS_TITLE,hasTitle);
        intent.putExtra(ContainerActivity.FRAMGNET_TYPE,type);
        context.startActivity(intent);
    }
}
