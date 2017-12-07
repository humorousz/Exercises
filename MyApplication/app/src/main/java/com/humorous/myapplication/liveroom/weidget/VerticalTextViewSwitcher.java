package com.humorous.myapplication.liveroom.weidget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;



import java.util.ArrayList;

/**
 * Created by zhangzhiquan on 2017/12/4.
 */

public class VerticalTextViewSwitcher extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 0;
    private static final int FLAG_STOP_AUTO_SCROLL = 1;

    private float mTextSize = 16 ;
    private int mPadding = 5;
    private int textColor = Color.BLACK;

    public boolean isScrolling() {
        return mIsScrolling;
    }

    public void setScrolling(boolean scrolling) {
        mIsScrolling = scrolling;
    }

    private boolean mIsScrolling = false;
    private OnMakeView mMakeViewListener;

    /**
     * @param textSize 字号
     * @param padding 内边距
     * @param textColor 字体颜色
     */
    public void setText(float textSize,int padding,int textColor) {
        mTextSize = textSize;
        mPadding = padding;
        this.textColor = textColor;
    }

    private OnItemClickListener itemClickListener;
    private Context mContext;
    private int currentId = -1;
    private ArrayList<CharSequence> textList;
    private Handler handler;

    public VerticalTextViewSwitcher(Context context) {
        this(context, null);
        mContext = context;
    }

    public VerticalTextViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        textList = new ArrayList<CharSequence>();
    }

    public void setAnimTime(long animDuration) {
        setFactory(this);
        Animation in = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0 );
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,-1.0f );
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    public void setOnMakeViewListener(OnMakeView makeViewListener){
        mMakeViewListener = makeViewListener;
    }

    /**
     * 间隔时间
     * @param time
     */
    public void setTextStillTime(final long time){
        handler =new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if (textList.size() > 0) {
                            currentId++;
                            setText(textList.get(currentId % textList.size()));
                        }
                        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL,time);
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                }
            }
        };
    }
    /**
     * 设置数据源
     * @param titles
     */
    public void setTextList(ArrayList<CharSequence> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
    }

    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
        mIsScrolling = true;
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
        mIsScrolling = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoScroll();
    }

    @Override
    public View makeView() {
        TextView view = null;
        if(mMakeViewListener != null){
            view = mMakeViewListener.makeView();
        }
        if(view == null){
            TextView t = new TextView(mContext);
            t.setGravity(Gravity.CENTER_VERTICAL);
            t.setMaxLines(1);
            t.setPadding(mPadding, mPadding, mPadding, mPadding);
            t.setTextColor(textColor);
            t.setTextSize(mTextSize);
            t.setClickable(true);
            view = t;
        }
        if(itemClickListener != null){
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                        itemClickListener.onItemClick(currentId % textList.size());
                    }
                }
            });
        }
        return view;
    }

    public int getCurrentPosition(){
       return  currentId % textList.size();
    }

    /**
     * 设置点击事件监听
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * 点击回调
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }

    public interface OnMakeView{
        TextView makeView();
    }



}
