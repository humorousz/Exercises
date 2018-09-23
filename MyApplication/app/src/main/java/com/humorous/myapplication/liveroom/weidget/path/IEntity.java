package com.humorous.myapplication.liveroom.weidget.path;

import android.graphics.Bitmap;

/**
 * @author zhangzhiquan
 * @date 2018/9/23
 * 绘制轨迹实体
 */
public interface IEntity {
    /**
     * 获取透明度
     * @return
     */
    float getAlpha();

    /**
     * 获取缩放比例
     * @return
     */
    float getScale();

    /**
     * 获取宽度
     * @return
     */
    int getWidth();

    /**
     * 获取高度
     * @return
     */
    int getHeight();

    /**
     * 设置透明度
     * @param alpha
     */
    void setAlpha(float alpha);

    /**
     * 设置缩放比例
     * @param scale
     */
    void setScale(float scale);

    /**
     * 设置宽度
     * @param width
     */
    void setWidth(int width);

    /**
     * 设置高度
     * @param height
     */
    void setHeight(int height);

    /**
     * 设置绘制点
     * @return
     */
    IPoint getPoint();

    /**
     * 设置绘制点
     * @param point
     */
    void setPoint(IPoint point);

    /**
     * 设置绘制图片
     * @param bitmap
     */
    void setBitmap(Bitmap bitmap);

    /**
     * 获取绘制图片
     * @return
     */
    Bitmap getBitmap();
}
