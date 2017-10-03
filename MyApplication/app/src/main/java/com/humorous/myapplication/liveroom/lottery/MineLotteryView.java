package com.humorous.myapplication.liveroom.lottery;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.humorous.myapplication.R;

import java.util.List;

/**
 * Created by zhangzhiquan on 2017/10/2.
 */

public class MineLotteryView extends RelativeLayout implements Animation.AnimationListener {
    private ImageView mLightImage, mLotteryImage;
    private Context mContext;
    private List<MineLotteryData> mData;
    private LinearLayout mLotteryContainer;
    private Animation inAnim, outAnim, numInAnim, numOutAnim;
    private int step = 0;
    private OnAnimationStateListener mListener;
    private boolean isCancel;

    public MineLotteryView(Context context) {
        this(context, null);
    }

    public MineLotteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineLotteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_mine_lottery_view, this);
        mLightImage = (ImageView) findViewById(R.id.lottery_bg_light);
        mLotteryContainer = (LinearLayout) findViewById(R.id.lottery_num_container);
        mLotteryImage = (ImageView) findViewById(R.id.lottery_img);
        mLightImage.setImageResource(R.drawable.lottery_light_bg);

        inAnim = AnimationUtils.loadAnimation(getContext(), R.anim.lottery_in);
        numInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.lottery_in);
        outAnim = AnimationUtils.loadAnimation(getContext(), R.anim.lottery_out);
        numOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.lottery_out);

        inAnim.setAnimationListener(this);
        numInAnim.setAnimationListener(this);
        outAnim.setAnimationListener(this);
        numOutAnim.setAnimationListener(this);
        outAnim.setStartOffset(1000);
        numOutAnim.setStartOffset(1000);

        AnimationDrawable anim = (AnimationDrawable) mLightImage.getDrawable();
        anim.start();

    }

    public void setOnAnimationStateListener(OnAnimationStateListener listener) {
        mListener = listener;
    }

    public void setData(List<MineLotteryData> mineLotteryData) {
        mData = mineLotteryData;
        isCancel = false;
        if (mData == null) {
            return;
        }
        step = 1;
        setLotteryImage();
        startAnimation(inAnim);
    }

    public void stopAnim() {
        isCancel = true;
        clearAnimation();
        mLotteryContainer.clearAnimation();
    }

    private void setLotteryImage() {
        if (step > mData.size())
            return;
        MineLotteryData data = mData.get(step - 1);
        int r = 0;
        switch (data.getLottery()) {
            case 10:
                r = R.drawable.lottery_10;
                break;
            case 100:
                r = R.drawable.lottery_100;
                break;
            case 500:
                r = R.drawable.lottery_100;
                break;
            case 5000:
                r = R.drawable.lottery_10;
                break;
        }
        mLotteryImage.setImageResource(r);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (mListener != null && animation == inAnim) {
            mListener.onStart();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (inAnim == animation && !isCancel) {
            startNextStepAnim();
        } else if (outAnim == animation) {
            if (mListener != null) {
                mListener.onEnd();
            }
        } else if (numInAnim == animation && !isCancel) {
            startNextStepAnim();
        } else if (numOutAnim == animation && !isCancel) {
            step++;
            setLotteryImage();
            mLotteryContainer.startAnimation(numInAnim);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void startNextStepAnim() {
        if (mData.size() <= step) {
            startAnimation(outAnim);
        } else {
            mLotteryContainer.startAnimation(numOutAnim);
        }
    }

    public interface OnAnimationStateListener {
        void onStart();

        void onEnd();
    }
}
