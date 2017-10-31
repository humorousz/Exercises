package com.humorous.myapplication.vectorDrawableTest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/10/30.
 */

public class GuardView extends RelativeLayout implements Animator.AnimatorListener{
    private static final String TAG = "GuardView";
    private static final int ANIM_BG_TIME = 200; //背景出现时间
    private static final int ANIM_BG_ROTATION = 2000; //光线旋转时间
    private static final int DELAY_TIME = 500; //延时时间
    private Context mContext;
    private ImageView mLightBg;
    private ImageView mStarBg;
    private ImageView mBigStarBg;
    private ImageView mLeftWing;
    private ImageView mRightWing;
    private ImageView mPlant;
    private ViewGroup mIconContainer;
    private TextView  mUserNameText;
    private TextView  mUserDescText;
    private ViewGroup mTypeIconContainer;
    private ImageView mTypeIcon;
    private ImageView mTypeIconStar;
    private AroundLightView mAroundLightView;

    public GuardView(Context context) {
        this(context,null);
    }

    public GuardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                startBgAnim();
                return false;
            }
        });
    }
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.layout_gurad_view,this,true);
        mLightBg = (ImageView) findViewById(R.id.guard_bg);
        mStarBg = (ImageView) findViewById(R.id.guard_star_bg);
        mBigStarBg = (ImageView) findViewById(R.id.guard_big_star_bg);
        mLeftWing = (ImageView) findViewById(R.id.left_wing);
        mRightWing = (ImageView) findViewById(R.id.right_wing);
        mPlant = (ImageView) findViewById(R.id.plant);
        mIconContainer = (ViewGroup) findViewById(R.id.icon_container);
        mUserNameText = (TextView) findViewById(R.id.text_user);
        mUserDescText = (TextView) findViewById(R.id.text_desc);
        mTypeIconContainer = (ViewGroup) findViewById(R.id.guard_type_icon_container);
        mTypeIcon = (ImageView) findViewById(R.id.guard_type_icon);
        mTypeIconStar = (ImageView) findViewById(R.id.guard_type_icon_star);
        mAroundLightView =  (AroundLightView) findViewById(R.id.guard_around_light);
    }
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startBgAnim();
//            }
//        },DELAY_TIME);
//    }

    private ObjectAnimator mBgScaleX,mBgScaleY;
    private AnimatorSet mBgAnimatorSet ;
    private void startBgAnim(){
        mBgScaleX = ObjectAnimator.ofFloat(mLightBg,"scaleX",0,1);
        mBgScaleY = ObjectAnimator.ofFloat(mLightBg,"scaleY",0,1);
        mBgAnimatorSet = new AnimatorSet();
        mBgAnimatorSet.playTogether(mBgScaleX,mBgScaleY);
        mBgAnimatorSet.addListener(this);
        mBgAnimatorSet.setDuration(ANIM_BG_TIME);
        mLightBg.setVisibility(VISIBLE);
        mBgAnimatorSet.start();
    }

    private ObjectAnimator mBgRotation;
    private void startBgAnimRotation(){
        mBgRotation = ObjectAnimator.ofFloat(mLightBg,"rotation",0,180);
        mBgRotation.setDuration(ANIM_BG_ROTATION);
        mBgRotation.start();
    }

    private ObjectAnimator mUserIconUp;
    private void startIconAnimUp(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mIconContainer.measure(w, h);
        int height = mIconContainer.getMeasuredHeight();
        float end = height * -1;
        float start = height * 0.3f ;
        mUserIconUp = ObjectAnimator.ofFloat(mIconContainer,"translationY",start,end);
        mUserIconUp.setDuration(320);
        mUserIconUp.addListener(this);
        mIconContainer.setVisibility(VISIBLE);
        mUserIconUp.addListener(this);
        mUserIconUp.start();
    }


    private ObjectAnimator mUserIconDown;
    private void startIconAnimDown(){
        mUserIconDown = ObjectAnimator.ofFloat(mIconContainer,"translationY",mIconContainer.getTranslationY(),0);
        mUserIconDown.setDuration(120);
        mUserIconDown.start();
    }

    private ObjectAnimator mLeftWingAnim;
    private void startWingAndPlantAnim(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mLeftWing.measure(w, h);
        mLeftWing.setPivotX(0);
        mLeftWing.setPivotY(mLeftWing.getY());
        mLeftWingAnim = ObjectAnimator.ofFloat(mLeftWing,"rotation",-180,0);
        mLeftWingAnim.setDuration(200);
        mLeftWing.setVisibility(VISIBLE);
        mLeftWingAnim.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(animation == mBgAnimatorSet){
            startBgAnimRotation();
            startIconAnimUp();
        }else if(animation == mUserIconUp){
            startIconAnimDown();
            startWingAndPlantAnim();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
