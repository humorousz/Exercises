package com.humorous.myapplication.liveroom.intoRoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;
import com.humorousz.uiutils.span.ChatBoxNetworkSpan;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zhangzhiquan on 2017/8/19.
 */

public class IntoRoomAnimatorController implements IntoRoomAnimatorView.OnAnimationStateListener {
    private static final String TAG = "AnimatorController";
    private Context mContext;
    private ViewGroup mContainer;
    private IntoRoomAnimatorView mAnimatorView;
    private Queue<CharSequence> mQueue;
    private boolean isRunning;

    public IntoRoomAnimatorController(Context context, ViewGroup container) {
        mContext = context;
        mContainer = container;
        mQueue = new LinkedList<>();
        isRunning = false;
    }


    public void addTask(CharSequence sequence) {
        Logger.d(TAG,"addTask");
        mQueue.offer(getSpan(new SpannableString(sequence)));
        takeTask();
    }

    public void takeTask() {
        if (!isRunning && !mQueue.isEmpty()) {
            Logger.d(TAG,"takeTask");
            startAnimator(mQueue.poll());
        }
    }

    public void clearTask(){
        if(mQueue != null){
            mQueue.clear();
        }
    }

    public void startAnimator(CharSequence sequence) {
        isRunning = true;
        if (mContainer == null)
            return;
        if (mAnimatorView == null) {
            mAnimatorView = new IntoRoomAnimatorView(mContext);
            mAnimatorView.setOnAnimationStateListener(this);
            mContainer.addView(mAnimatorView);
        }
        if(mAnimatorView.getVisibility() == View.INVISIBLE){
            mAnimatorView.setVisibility(View.VISIBLE);
        }
        mAnimatorView.setData(sequence);
        Logger.d(TAG,"start anim");
    }

    public void release() {
        mContainer = null;
        mContext = null;
        mAnimatorView = null;
        if (mQueue != null && mQueue.size() != 0) {
            mQueue.clear();
        }
        mQueue = null;
    }

    public void init(){
        isRunning = false;
    }

    public void clear(){
        clearTask();
        if(isRunning){
            mAnimatorView.stopAnim();
            Logger.d(TAG,"stop Anim");
        }
        mAnimatorView.setVisibility(View.INVISIBLE);
        isRunning = false;
    }


    private CharSequence getSpan(SpannableString str){
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ChatBoxNetworkSpan span = new ChatBoxNetworkSpan();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_pointer);
        int btmWidth = bitmap.getWidth();
        int btmHeight = bitmap.getHeight();

        BitmapDrawable bitmapD  = new BitmapDrawable(bitmap);
        int finalWidth = (int) (btmWidth * (UIUtils.dip2px(16) / (float) btmHeight));
        bitmapD.setBounds(0, 0, finalWidth, (UIUtils.dip2px(16) ));
        span.setDrawable(bitmapD);
        SpannableString spString = new SpannableString("a ");
        spString.setSpan(span,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.append(spString);
        ssb.append(setSpanColor(str, Color.parseColor("#fff7c0")));
        return ssb;
    }

    private SpannableString setSpanColor(SpannableString span,int color){
        ForegroundColorSpan mColorTextSpan = new ForegroundColorSpan(color);
        span.setSpan(mColorTextSpan, 0, span.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return span;
    }


    @Override
    public void onStart() {
        isRunning = true;
    }

    @Override
    public void onEnd() {
        isRunning = false;
        takeTask();
    }
}
