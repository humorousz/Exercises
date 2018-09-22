package com.humorous.myapplication.liveroom.weidget.path;

import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/9/22
 * 轨迹动画View
 */
public interface IPathView {
    /**
     * 绘制礼物
     * @param entities 绘制路径
     */
    void drawPath(List<? extends IEntity> entities);

    /**
     * 获取高
     * @return
     */
    int getPathViewHeight();

    /**
     * 获取宽
     * @return
     */
    int getPathViewWidth();
}
