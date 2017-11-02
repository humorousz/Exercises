package com.humorousz.uiutils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.R;
import com.humorousz.uiutils.helper.UIUtils;

/**
 * Created by zhangzhiquan on 2017/9/25.
 */

public class CommonTipsView extends LinearLayout{
    private static final String TAG = "CommonTipsView";
    private static final int MARGIN_DIP = 10;
    private LinearLayout mTopArrowContainer;
    private LinearLayout mBottomArrowContainer;
    private TextView mTipsTextView;
    private ImageView mArrowImage;
    private Context mContext;

    private int mArrowGravity;
    private int mTextGravity;
    private String mText ;
    private int mTextSize = 15;
    private int  mTipsColor;
    private int mArrowMarginLeft;
    private int mArrowMarginRight;

    public CommonTipsView(Context context) {
        this(context,null);
    }

    public CommonTipsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommonTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_common_tips_view,this);
        initAttrs(attrs,defStyleAttr);
        initView();

    }

    private void initAttrs(AttributeSet attrs,int defStyleAttr){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CommonTipsView, defStyleAttr, 0);
        mArrowGravity = a.getInt(R.styleable.CommonTipsView_arrow_gravity, Gravity.BOTTOM | Gravity.RIGHT);
        mText = a.getString(R.styleable.CommonTipsView_android_text);
        mTextSize = a.getDimensionPixelSize(R.styleable.CommonTipsView_android_textSize, mTextSize);
        mTipsColor = a.getColor(R.styleable.CommonTipsView_tips_color, mContext.getResources().getColor(R.color.common_tips_color));
        mTextGravity = a.getInt(R.styleable.CommonTipsView_android_gravity, Gravity.LEFT);
        mArrowMarginLeft = a.getDimensionPixelSize(R.styleable.CommonTipsView_arrow_margin_left,0);
        mArrowMarginRight = a.getDimensionPixelSize(R.styleable.CommonTipsView_arrow_margin_right,0);
        a.recycle();
    }

    private void initView(){
        mTopArrowContainer = (LinearLayout) findViewById(R.id.top_arrow_container);
        mBottomArrowContainer = (LinearLayout) findViewById(R.id.bottom_arrow_container);
        mTipsTextView = (TextView) findViewById(R.id.tips_text);

        mTipsTextView.setText(mText);
        mTipsTextView.setTextSize(mTextSize);
        mTipsTextView.setGravity(mTextGravity);
        GradientDrawable gradientDrawable = (GradientDrawable) mTipsTextView.getBackground();
        gradientDrawable.setColor(mTipsColor);
        initArrow();
    }


    private void initArrow(){
        mArrowImage = new ImageView(mContext);
        mArrowImage.setImageResource(R.drawable.common_tips_arrow);
        mArrowImage.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(UIUtils.dip2px(mContext,10),UIUtils.dip2px(mContext,5));
        mArrowImage.setLayoutParams(layoutParams);
        setArrow();
        if((Gravity.VERTICAL_GRAVITY_MASK & mArrowGravity) == Gravity.BOTTOM){
            mBottomArrowContainer.addView(mArrowImage);
        }else {
            mTopArrowContainer.addView(mArrowImage);
        }
    }

    private void setArrow(){
        LinearLayout.LayoutParams layoutParams = (LayoutParams) mArrowImage.getLayoutParams();
        if((Gravity.VERTICAL_GRAVITY_MASK & mArrowGravity) == Gravity.BOTTOM){
            if((Gravity.HORIZONTAL_GRAVITY_MASK & mArrowGravity) == Gravity.LEFT){
                int leftMargin = mArrowMarginLeft == 0? UIUtils.dip2px(mContext,MARGIN_DIP):mArrowMarginLeft;
                layoutParams.setMargins(leftMargin,0,mArrowMarginRight,0);
                mBottomArrowContainer.setGravity(Gravity.LEFT);
            }else if((Gravity.HORIZONTAL_GRAVITY_MASK & mArrowGravity) == Gravity.RIGHT){
                int rightMargin = mArrowMarginRight == 0? UIUtils.dip2px(mContext,MARGIN_DIP):mArrowMarginRight;
                layoutParams.setMargins(mArrowMarginLeft,0,rightMargin,0);
                mBottomArrowContainer.setGravity(Gravity.RIGHT);
            }else {
                layoutParams.setMargins(0,0,0,0);
                mBottomArrowContainer.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            mArrowImage.setLayoutParams(layoutParams);
        }else {
            mArrowImage.setRotation(180);
            if((Gravity.HORIZONTAL_GRAVITY_MASK & mArrowGravity) == Gravity.LEFT){
                int leftMargin = mArrowMarginLeft == 0? UIUtils.dip2px(mContext,MARGIN_DIP):mArrowMarginLeft;
                layoutParams.setMargins(leftMargin,0,mArrowMarginRight,0);
                mTopArrowContainer.setGravity(Gravity.LEFT);
            }else if((Gravity.HORIZONTAL_GRAVITY_MASK & mArrowGravity) == Gravity.RIGHT){
                int rightMargin = mArrowMarginRight == 0? UIUtils.dip2px(mContext,MARGIN_DIP):mArrowMarginRight;
                layoutParams.setMargins(mArrowMarginLeft,0,rightMargin,0);
                mTopArrowContainer.setGravity(Gravity.RIGHT);
            }else {
                layoutParams.setMargins(0,0,0,0);
                mTopArrowContainer.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            mArrowImage.setLayoutParams(layoutParams);
        }
    }



    public void setText(CharSequence charSequence){
        mTipsTextView.setText(charSequence);
        mTipsTextView.requestLayout();
    }

    public void setArrowGravity(int gravity){
        mArrowGravity = gravity;
        setArrow();
    }


    public TextView getTipsTextView(){
        return  mTipsTextView;
    }

}
