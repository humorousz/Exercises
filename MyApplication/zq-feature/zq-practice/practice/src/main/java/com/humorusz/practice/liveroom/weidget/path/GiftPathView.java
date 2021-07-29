package com.humorusz.practice.liveroom.weidget.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.humorousz.commonutils.log.Logger;

import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/9/23
 */
public class GiftPathView extends View implements IPathView {
    private List<? extends IEntity> mEntities;
    public GiftPathView(Context context) {
        super(context);
    }

    public GiftPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mEntities == null){
            return;
        }
        Paint paint = new Paint();
        for(IEntity entity : mEntities){
            int left = (int) (entity.getPoint().getX() - entity.getWidth()/2);
            int right = (int) (entity.getPoint().getX() + entity.getWidth()/2);
            int top = (int) (entity.getPoint().getY() - entity.getHeight()/2);
            int bottom = (int) (entity.getPoint().getY() + entity.getHeight() /2);
            Rect rect = new Rect(left,top,right,bottom);
            canvas.drawBitmap(entity.getBitmap(),null,rect,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Logger.d("MRZ","pos x:"+event.getRawX()+" y:"+event.getRawY());
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void drawPath(List<? extends IEntity> entities) {
        if(entities == null || entities.size() == 0){
            return;
        }
        mEntities = entities;
        postInvalidate();
    }

    @Override
    public int getPathViewHeight() {
        return getWidth();
    }

    @Override
    public int getPathViewWidth() {
        return getHeight();
    }
}
