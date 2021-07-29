package com.humorusz.practice.liveroom.weidget.path;

/**
 * @author zhangzhiquan
 * @date 2018/9/23
 */
public class GiftPoint implements IPoint {
    private float x,y;
    public GiftPoint(float x,float y){
        this.x = x;
        this.y = y;
    }
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
