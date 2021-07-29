package com.humorusz.practice.liveroom.controller;

/**
 * @author zhangzhiquan
 * @date 2018/2/7
 * 自动发言用户进场控制
 */

public class AutoController {
    private OnAutoListener mListener;
    private volatile boolean isRunning = false;
    private volatile boolean isDestroy = false;
    private Thread mMessageThread;
    private static final int DELAY_TIME = 500;
    public AutoController(OnAutoListener listener){
        if(listener == null){
            throw new NullPointerException("Listener can not be null");
        }
        mListener = listener;
    }

    /**
     * 打开自动控制
     * @return true 已经打开，false 还没打开
     */
    public boolean startAuto(){
        if(isRunning){
            return true;
        }
        if(mMessageThread == null){
            mMessageThread = new Thread(new ConsumerRunnable("message",DELAY_TIME));
        }
        mMessageThread.start();
        isRunning = true;
        return false;
    }

    public void stopAuto(){
        isRunning = false;
        mMessageThread.interrupt();
    }

    public void destroy(){
        isDestroy = true;
        if(mMessageThread != null){
            mMessageThread.interrupt();
        }
    }

    private class ConsumerRunnable implements Runnable{
        String name;
        private int delayTime = 1000;
        ConsumerRunnable(String name,int delayTime){
            this.name = name;
            this.delayTime = delayTime;
        }
        @Override
        public void run() {
            while (isRunning){
                if(mListener != null){
                    mListener.autoSendMessage();
                    mListener.autoComeInUser();
                }
                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 自动发言回调
     */
    public interface OnAutoListener{
        /**
         * 自动发言
         * 具体如何发言，可自行实现
         */
        void autoSendMessage();

        /**
         * 自动用户进场
         */
        void autoComeInUser();
    }
}
