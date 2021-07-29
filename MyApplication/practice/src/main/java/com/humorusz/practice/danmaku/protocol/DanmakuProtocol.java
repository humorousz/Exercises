package com.humorusz.practice.danmaku.protocol;

import android.view.View;

/**
 * @author zhangzhiquan
 * on 2018/5/20
 */
public interface DanmakuProtocol {
    /**
     * resume danmaku
     */
    void resume();

    /**
     * pause danmaku
     */
    void pause();

    /**
     * show danmaku
     */
    void show();

    /**
     * hide damaku
     */
    void hide();

    /**
     * destroy damaku
     */
    void destroy();

    /**
     * clean screen damaku
     */
    void clean();

    /**
     * get view
     * @return
     */
    View getView();

    /**
     * add danmaku
     * @param data
     */
    void addDanmaku(IDanmakuData data);

}
