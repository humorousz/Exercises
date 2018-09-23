package com.humorous.myapplication.liveroom.weidget.path;

import android.graphics.Bitmap;

/**
 * @author zhangzhiquan
 * @date 2018/9/23
 */
public class GiftEntity implements IEntity {
    private float mAlpha,mScale;
    private int mWidth,mHeight;
    private Bitmap mBitmap;
    private IPoint mPoint;

    public GiftEntity(){

    }
    /**
     * 构造函数
     * @param alpha 透明度
     * @param scale 缩放
     * @param width 宽
     * @param height 高
     * @param bitmap 图片
     * @param point 坐标
     */
    public GiftEntity(float alpha,float scale,int width,int height,Bitmap bitmap,IPoint point){
        mAlpha = alpha;
        mScale = scale;
        mWidth = width;
        mHeight = height;
        mBitmap = bitmap;
        mPoint = point;
    }
    @Override
    public float getAlpha() {
        return mAlpha;
    }

    @Override
    public float getScale() {
        return mScale;
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    @Override
    public void setScale(float scale) {
        mScale = scale;
    }

    @Override
    public void setWidth(int width) {
        mWidth = width;
    }

    @Override
    public void setHeight(int height) {
        mHeight = height;
    }

    @Override
    public IPoint getPoint() {
        return mPoint;
    }

    @Override
    public void setPoint(IPoint point) {
        mPoint = point;
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }
}
