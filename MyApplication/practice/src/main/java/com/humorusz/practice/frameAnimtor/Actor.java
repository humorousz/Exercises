package com.humorusz.practice.frameAnimtor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhangzhiquan on 2017/8/2.
 */

public class Actor extends BaseActor implements SurfaceHolder.Callback {
    private static final String TAG = "Actor";
    private WeakReference<Context> mContext;
    private BlockingQueue<Bitmap> mQueue;
    private SurfaceHolder mHoler;
    private int totalCount;
    public Actor(Context context) {
        super(context);
        mContext = new WeakReference<Context>(context);
        mQueue = new ArrayBlockingQueue<>(10);
        mHoler = this.getHolder();
        mHoler.addCallback(this);
        setZOrderOnTop(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        initTotalCount();
        startDecode();
    }

    private void initTotalCount(){
        try {
            if(mContext.get() != null){
                totalCount = mContext.get().getResources().getAssets().list("f2").length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startDecode(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    decodeBitmap();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        DrawThread thread = new DrawThread();
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        rect=new Rect(0,0,width,height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mQueue != null){
           while(!mQueue.isEmpty()){
               mQueue.poll().recycle();
           }
        }
    }

    private Bitmap getImage() throws InterruptedException {
        return mQueue.take();
    }

    private Bitmap getImageFromAssetsFile(int id){
        if(mContext.get() == null)
            return null;
        Bitmap image = null;
        AssetManager am = mContext.get().getResources().getAssets();
        try {
            InputStream is = am.open("f2/"+id+".png");
            image = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void decodeBitmap() throws InterruptedException {
        for(int i = 0;i<totalCount;i++){
            mQueue.put(getImageFromAssetsFile(i+1));
            Logger.d(TAG,"decode "+i);
        }
    }

    private class DrawThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
                try {
                    Bitmap bitmap = getImage();
                    Canvas canvasstart = mHoler.lockCanvas();
                    if (bitmap == null || canvasstart == null )
                        break;
                    doDraw(canvasstart, bitmap);
                    mHoler.unlockCanvasAndPost(canvasstart);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doDraw(Canvas canvas,Bitmap bitmap){
//			*important*背景透明
        if(canvas == null)
            return;
        int left = UIUtils.getScreenWidth()/2 - bitmap.getWidth()/2;
        int top = UIUtils.getScreenHeight()/2 - bitmap.getHeight()/2;
        Paint paint = new Paint();//实例化画笔
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.save();
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight())
                ,new Rect(left,top,left+bitmap.getWidth(),top+bitmap.getHeight()),paint);
        canvas.restore();
        bitmap.recycle();
    }

}
