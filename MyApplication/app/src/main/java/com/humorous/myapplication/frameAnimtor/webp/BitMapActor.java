package com.humorous.myapplication.frameAnimtor.webp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.facebook.animated.webp.WebPImage;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.humorous.myapplication.frameAnimtor.BaseActor;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangzhiquan on 2017/8/3.
 */

public class BitMapActor extends BaseActor implements SurfaceHolder.Callback {
    private static final String TAG = "BitMapActor";
    private AnimatedImage mWebPImage;
    private SurfaceHolder mHolder;
    private DrawThread mThread;
    private String mFilePath;

    public BitMapActor(Context context,String filePath) {
        super(context);
        mFilePath = filePath;
        mWebPImage = createWebP();
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new DrawThread();
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopAll();
    }

    public boolean stopAll(){
        if(mThread != null){
            mThread.stop = true;
            mThread = null;
        }
        mWebPImage = null;
        return true;
    }


    private WebPImage createWebP(){
        AssetManager am = getContext().getResources().getAssets();
        try {
            InputStream in = am.open(mFilePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int rc;
            while ((rc = in.read(buff,0,1024)) > 0){
                out.write(buff,0,rc);
            }
            byte[] bytes = out.toByteArray();
            return WebPImage.create(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private class DrawThread extends Thread{
        int count = 0;
        boolean stop = false;
        private long duration = 0;
        long lastTime = 0;
        @Override
        public void run() {
            super.run();
            if(mListener != null){
                mListener.onStartAnim();
            }
            Bitmap bitmap = Bitmap.createBitmap(mWebPImage.getWidth(),mWebPImage.getHeight(), Bitmap.Config.ARGB_8888);
            try {
                int lastCount = 0;
                long stateTime = 0;
                while (!stop && count < mWebPImage.getFrameCount() && mWebPImage != null) {
                    lastTime = SystemClock.uptimeMillis();
                    Canvas canvasStart = mHolder.lockCanvas();
                    if (bitmap == null || canvasStart == null)
                        break;
                    if(lastCount != count){
                        mWebPImage.getFrame(count).renderFrame(mWebPImage.getWidth(), mWebPImage.getHeight(), bitmap);
                        doDrawBottom(canvasStart, bitmap, UIUtils.getScreenWidth(), UIUtils.getScreenHeight());
                    }
                    mHolder.unlockCanvasAndPost(canvasStart);
                    duration = (SystemClock.uptimeMillis() - lastTime);
                    stateTime += duration;
                    if(stateTime >= mWebPImage.getFrame(count).getDurationMs()){
                        lastCount = count;
                        count++;
                        stateTime = 0;
                    }
                }
            } finally {
                if(bitmap != null){
                    bitmap.recycle();
                }
                if(mListener != null){
                    mListener.onEndAnim();
                }
            }
        }
    }

}
