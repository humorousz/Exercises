package com.humorousz.commonutils.handler;

import android.os.Message;

/**
 * @author Created by zhangzhiquan on 2018/2/5.
 */

public interface HandlerCallback {
    /**
     * 处理消息
     * @param message
     */
    void handleMessage(Message message);
}
