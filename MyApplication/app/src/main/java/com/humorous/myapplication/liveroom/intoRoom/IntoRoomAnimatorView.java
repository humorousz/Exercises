package com.humorous.myapplication.liveroom.intoRoom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/8/19.
 */

public class IntoRoomAnimatorView extends LinearLayout implements Animation.AnimationListener {
    private static final String TAG = "IntoRoomAnimatorView";
    private TextView mTextView;
    private FrameLayout mTextContainer;
    private ImageView mStar;
    private Context mContext;
    private Animation inAnimation,outAnimation;
    private OnAnimationStateListener mListener;
    Interpolator interpolator;
    private boolean isCancel;
    private boolean isRunning;
    public IntoRoomAnimatorView(Context context) {
        this(context,null);
    }

    public IntoRoomAnimatorView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public IntoRoomAnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

    }

    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.layout_into_room_view,this);
        mTextContainer = (FrameLayout) findViewById(R.id.text_container);
        mStar = (ImageView) findViewById(R.id.star_img);
        mTextView = (TextView) findViewById(R.id.text_into);
        inAnimation = AnimationUtils.loadAnimation(mContext,R.anim.into_right_to_left);
        inAnimation.setFillAfter(true);
        inAnimation.setAnimationListener(this);
        outAnimation = AnimationUtils.loadAnimation(mContext,R.anim.into_out);
        outAnimation.setFillAfter(true);
        outAnimation.setAnimationListener(this);
        mStar.setImageResource(R.drawable.aa);
        mStar.clearAnimation();
        interpolator = new EaseCubicInterpolator(0f,1.17f,0f,0.9f);
    }

    public void setData(CharSequence data){
        if(isRunning)
            return;
        mTextView.setText(data);
        AnimationDrawable anim = (AnimationDrawable) mStar.getDrawable();
        anim.start();
        isCancel = false;
        Interpolator interpolator = new EaseCubicInterpolator(0f,1.17f,0f,0.9f);
        inAnimation.setInterpolator(interpolator);
        mTextContainer.startAnimation(inAnimation);
    }

    public void stopAnim(){
        isCancel = true;
        mTextContainer.clearAnimation();
        isRunning = false;

    }

    public void setOnAnimationStateListener(OnAnimationStateListener listener){
        mListener = listener;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(mListener != null){
            mListener.onStart();
        }
        if(animation == inAnimation){
            isRunning = true;
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation == inAnimation){
            if(!isCancel){
                mTextContainer.startAnimation(outAnimation);
            }
        }else if(animation == outAnimation){
            isRunning = false;
            AnimationDrawable anim = (AnimationDrawable) mStar.getDrawable();
            anim.stop();
            if(mListener != null){
                mListener.onEnd();
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface OnAnimationStateListener{
        void onStart();
        void onEnd();
    }
}
