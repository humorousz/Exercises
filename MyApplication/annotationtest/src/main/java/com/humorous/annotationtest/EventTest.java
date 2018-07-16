package com.humorous.annotationtest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author zhangzhiquan
 * @date 2018/7/13
 */
public class EventTest {
    public EventTest(){
        EventBus.getDefault().register(this);
    }


    public void release(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event e){

    }
}
