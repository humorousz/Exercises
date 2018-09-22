package com.humorous.myapplication.liveroom.weidget.path;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/9/22
 * 轨迹的功能定义
 */
public interface IPath {
    /**
     * Drawing
     */
    int STATE_DRAWING = 1;
    /**
     * finish
     */
    int STATE_FINISH = 2;
    @IntDef({STATE_DRAWING,STATE_FINISH})
    @Retention(RetentionPolicy.SOURCE)
    @interface PathState{}
    /**
     * 绘制轨迹
     * @param view 绘制View
     * @param bitmap 绘制图片
     * @param points 绘制实体
     */
   void drawPath(IPathView view,Bitmap bitmap,List<? extends IPoint> points);

    /**
     * 取消绘制
     */
   void cancel();

    /**
     * 获取绘制状态
     * @return
     */
   @PathState int getState();

    /**
     * 设置状态改变监听
     * @param listener
     */
   void setOnStateChangeListener(OnStateChangeListener listener);

   interface OnStateChangeListener{
       /**
        * 开始绘制
        * @param path 绘制路径
        */
       void onStartDraw(IPath path);

       /**
        * 结束绘制 绘制路径
        * @param path
        */
       void onFinish(IPath path);
   }
}
