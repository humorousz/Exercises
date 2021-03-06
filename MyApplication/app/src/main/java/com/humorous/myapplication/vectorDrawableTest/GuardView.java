package com.humorous.myapplication.vectorDrawableTest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.ImageLoaderHelper;

/**
 *
 * Created by zhangzhiquan on 2017/10/30.
 */

public class GuardView extends RelativeLayout implements Animator.AnimatorListener{
    private static final String TAG = "GuardView";
    private static final int ANIM_BG_TIME = 200; //背景出现时间
    private static final int ANIM_BG_ROTATION = 3000; //光线旋转时间
    private static final int STAR_DELAY_TIME = 200; //背景大星星的延时出场时间
    private static final int ICON_UP = 320; //头像抬起时间
    private static final int ICON_DOWN = 120; //头像下降时间
    private static final int WING_UP = 200; // 翅膀展开时间
    private static final int STAR_BG_TIME = 400;//星星背景时间
    private static final int TEXT_AND_ICON_TIME = 200;//名称和icon的时间
    private static final int ITEM_QUIT_TIME = 400; //退出时间
    private static final int ITEM_DELAY_TIME = 1700; //退出前等待时间
    private static final int STAR_BLINK_TIME = 600; //icon星星闪烁时间
    private Context mContext;
    private ImageView mLightBg;
    private ImageView mStarBg;
    private ImageView mBigStarBg;
    private ImageView mLeftWing;
    private ImageView mRightWing;
    private ImageView mPlant;
    private ImageView mUserIcon;
    private ImageView mGuardTypeIcon;
    private ViewGroup mIconContainer;
    private TextView  mUserNameText;
    private TextView  mUserDescText;
    private ViewGroup mTypeIconContainer;
    private ImageView mTypeIconStar;
    private ViewGroup mItemContainer;
    private GuardStateListener mListener;
    private boolean isEndAnim = false;
    private GuardMessage mMsg;

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
    }

    public void setGuardStateListener(GuardStateListener listener){
        mListener = listener;
    }
    public void setGuardMessage(GuardMessage msg){
        mMsg = msg;
        mUserNameText.setText(msg.getName());
        ImageLoaderHelper.displayCircleImage(msg.getLink(),mUserIcon);
        if(mMsg.getType()== GuardMessage.GUARD_YEAR){
            mLightBg.setImageResource(R.drawable.guard_bg_year);
            mGuardTypeIcon.setImageResource(R.drawable.guard_icon_year);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startBgAnim();
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
        mTypeIconStar = (ImageView) findViewById(R.id.guard_type_icon_star);
        mItemContainer = (ViewGroup) findViewById(R.id.item_container);
        mUserIcon = (ImageView) findViewById(R.id.user_icon);
        mGuardTypeIcon =  (ImageView) findViewById(R.id.guard_type_icon);
    }

    private ObjectAnimator mBgScaleX,mBgScaleY;
    private AnimatorSet mBgAnimatorSet ;

    /**
     * 背景出现动画
     */
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

    /**
     * 背景旋转动画
     */
    private void startBgAnimRotation(){
        mBgRotation = ObjectAnimator.ofFloat(mLightBg,"rotation",0,270);
        mBgRotation.setInterpolator(new LinearInterpolator());
        mBgRotation.setDuration(ANIM_BG_ROTATION);
        mBgRotation.start();
    }

    private ObjectAnimator mUserIconUp;

    /**
     * 头像升起动画
     */
    private void startIconAnimUp(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mItemContainer.measure(w, h);
        int height = mItemContainer.getMeasuredHeight();
        float end = height * 0.2f * -1;
        float start = height * 0.2f ;
        mUserIconUp = ObjectAnimator.ofFloat(mItemContainer,"translationY",start,end);
        mUserIconUp.setDuration(ICON_UP);
        mUserIconUp.addListener(this);
        mIconContainer.setVisibility(VISIBLE);
        mUserIconUp.start();
    }


    private ObjectAnimator mUserIconDown;
    private ObjectAnimator mLeftWingAnim;
    private ObjectAnimator mRightWingAnim;
    private ObjectAnimator mPlantScaleX;
    private ObjectAnimator mPlantScaleY;
    private ObjectAnimator mStarBgAnim;
    private ObjectAnimator mBigStarBgAnim;
    private ObjectAnimator mUserNameTextAnim;
    private ObjectAnimator mUserDescTextAnim;
    private ObjectAnimator mTypeIconContainerAnim;

    /**
     * 最后动画的组合：头像下降，翅膀展开，植物、名称、Icon等出现
     */
    private void lastAnim(){
        mUserIconDown = ObjectAnimator.ofFloat(mItemContainer,"translationY",mItemContainer.getTranslationY(),0);
        mUserIconDown.setDuration(ICON_DOWN);
//        mUserIconDown.addListener(this);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mLeftWing.measure(w, h);
        mLeftWing.setPivotX(mLeftWing.getWidth());
        mLeftWing.setPivotY(mLeftWing.getHeight());
        mLeftWingAnim = ObjectAnimator.ofFloat(mLeftWing,"rotation",-100,0);
        mLeftWingAnim.setDuration(WING_UP);

        mRightWing.measure(w,h);
        mRightWing.setPivotX(0);
        mRightWing.setPivotY(mRightWing.getHeight());
        mRightWingAnim = ObjectAnimator.ofFloat(mRightWing,"rotation",100,0);
        mRightWingAnim.setDuration(WING_UP);

        mPlantScaleX = ObjectAnimator.ofFloat(mPlant,"scaleX",0,1);
        mPlantScaleY = ObjectAnimator.ofFloat(mPlant,"scaleY",0,1);
        mPlantScaleX.setDuration(WING_UP/2);
        mPlantScaleY.setDuration(WING_UP/2);

        mStarBgAnim = ObjectAnimator.ofFloat(mStarBg,"alpha",1,0);
        mBigStarBgAnim = ObjectAnimator.ofFloat(mBigStarBg,"alpha",1,0);

        mStarBgAnim.setDuration(STAR_BG_TIME);
        mBigStarBgAnim.setDuration(STAR_BG_TIME);

        mStarBgAnim.setRepeatCount(ValueAnimator.INFINITE);
        mStarBgAnim.setRepeatMode(ValueAnimator.REVERSE);
        mBigStarBgAnim.setRepeatCount(ValueAnimator.INFINITE);
        mBigStarBgAnim.setRepeatMode(ValueAnimator.REVERSE);
        mStarBgAnim.addListener(this);

        mUserNameTextAnim = ObjectAnimator.ofFloat(mUserNameText,"alpha",0,1);
        mUserNameTextAnim.setDuration(TEXT_AND_ICON_TIME);
        mUserDescTextAnim = ObjectAnimator.ofFloat(mUserDescText,"alpha",0,1);
        mUserDescTextAnim.setDuration(TEXT_AND_ICON_TIME);

        mTypeIconContainerAnim = ObjectAnimator.ofFloat(mTypeIconContainer,"alpha",0,1);
        mTypeIconContainerAnim.addListener(this);
        mTypeIconContainerAnim.setDuration(TEXT_AND_ICON_TIME);

        mLeftWing.setVisibility(VISIBLE);
        mRightWing.setVisibility(VISIBLE);
        mPlant.setVisibility(VISIBLE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(mLeftWingAnim,mRightWingAnim
                ,mPlantScaleX,mPlantScaleY,mUserIconDown);
        animatorSet.addListener(this);
        animatorSet.start();

    }
    AnimatorSet animatorSet;

    private ObjectAnimator mTypeIconStarScaleX,mTypeIconStarScaleY;
    private AnimatorSet mIconStarSet;

    /**
     * Icon右上角星星闪烁动画
     */
    private void startIconStarAnim(){
        mTypeIconStarScaleX = ObjectAnimator.ofFloat(mTypeIconStar,"scaleX",1,0);
        mTypeIconStarScaleY = ObjectAnimator.ofFloat(mTypeIconStar,"scaleY",1,0);
        mTypeIconStarScaleX.setDuration(STAR_BLINK_TIME);
        mTypeIconStarScaleY.setDuration(STAR_BLINK_TIME);
        mTypeIconStarScaleX.setRepeatCount(ValueAnimator.INFINITE);
        mTypeIconStarScaleY.setRepeatCount(ValueAnimator.INFINITE);
        mTypeIconStarScaleX.setRepeatMode(ValueAnimator.REVERSE);
        mTypeIconStarScaleY.setRepeatMode(ValueAnimator.REVERSE);

        mTypeIconStar.setVisibility(VISIBLE);
        mIconStarSet = new AnimatorSet();
        mIconStarSet.playTogether(mTypeIconStarScaleX,mTypeIconStarScaleY);
        mIconStarSet.start();
    }


    private AnimatorSet mItemQuitAnimSet;
    private  ValueAnimator animator,bgAnimator;

    /**
     * 退出动画
     */
    private void quitAnim(){
        animator = ValueAnimator.ofFloat(1,0);
        animator.addListener(this);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
                mItemContainer.setScaleX(curValue);
                mItemContainer.setScaleY(curValue);
            }
        });
        animator.setDuration(ITEM_QUIT_TIME);
        animator.setStartDelay(ITEM_DELAY_TIME);

        bgAnimator = ValueAnimator.ofFloat(1,0);
        bgAnimator.addListener(this);
        bgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
                mLightBg.setScaleX(curValue);
                mLightBg.setScaleY(curValue);

                mStarBg.setScaleX(curValue);
                mStarBg.setScaleY(curValue);

                mBigStarBg.setScaleX(curValue);
                mBigStarBg.setScaleY(curValue);
            }
        });
        bgAnimator.setDuration(ITEM_QUIT_TIME/2);
        bgAnimator.setStartDelay(ITEM_DELAY_TIME + 200);


        mItemQuitAnimSet = new AnimatorSet();
        mItemQuitAnimSet.addListener(this);
        mItemQuitAnimSet.playTogether(animator,bgAnimator);
        mItemQuitAnimSet.start();

    }

    /**
     * 取消反复执行的动画，防止内存泄漏
     */
    private void clearAnim(){
        if (mStarBgAnim!=null){
            mStarBgAnim.cancel();
        }
        if(mBigStarBgAnim != null){
            mBigStarBgAnim.cancel();
        }
        if(mTypeIconStarScaleX != null){
            mTypeIconStarScaleX.cancel();
        }
        if(mTypeIconStarScaleY != null){
            mTypeIconStarScaleY.cancel();
        }
        mBgAnimatorSet.removeAllListeners();
        mUserIconUp.removeAllListeners();
        mUserIconDown.removeAllListeners();
        mStarBgAnim.removeAllListeners();
        mTypeIconContainerAnim.removeAllListeners();
        animator.removeAllListeners();
        bgAnimator.removeAllListeners();
        mItemQuitAnimSet.removeAllListeners();
    }

    @Override
    public void onAnimationStart(Animator animation) {

        if(mListener != null && animation == mBgAnimatorSet){
            mListener.onStartAnim();
        }else if(animation == mStarBgAnim) {
            if (mBigStarBg.getVisibility() != VISIBLE) {
                mBigStarBg.setVisibility(VISIBLE);
                mBigStarBgAnim.setStartDelay(STAR_DELAY_TIME);
                mBigStarBgAnim.start();
            }
        }
//        else if(animation == mUserIconDown){
//            mLeftWing.setVisibility(VISIBLE);
//            mRightWing.setVisibility(VISIBLE);
//            mPlant.setVisibility(VISIBLE);
//            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.playTogether(mLeftWingAnim,mRightWingAnim
//                    ,mPlantScaleX,mPlantScaleY);
//            animatorSet.start();
//        }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(animation == mBgAnimatorSet){
            startIconAnimUp();
        }else if(animation == mUserIconUp){
            startBgAnimRotation();
            lastAnim();
        }else if(animation == animatorSet){
            mLeftWing.setVisibility(VISIBLE);
            mRightWing.setVisibility(VISIBLE);
            mPlant.setVisibility(VISIBLE);
            mStarBg.setVisibility(VISIBLE);
            mUserNameText.setVisibility(VISIBLE);
            mUserDescText.setVisibility(VISIBLE);
            mTypeIconContainer.setVisibility(VISIBLE);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(mStarBgAnim
                    ,mUserNameTextAnim,mUserDescTextAnim,mTypeIconContainerAnim);
            animatorSet.start();
        }else if(animation == mTypeIconContainerAnim){
            startIconStarAnim();
            quitAnim();
        }else if(animation == mItemQuitAnimSet){
            clearAnim();
            if(mListener != null){
                isEndAnim = true;
                mListener.onEndAnim();
            }
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(!isEndAnim){
            Logger.d(TAG,"not cancel");
            clearAnim();
        }
    }

    public interface GuardStateListener{
        void onStartAnim();
        void onEndAnim();
    }
}
