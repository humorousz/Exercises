package com.humorousz.uiutils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.R;

/**
 * Created by zhangzhiquan on 2017/9/25.
 */

public class CommonTipsView extends LinearLayout{
    private static final String TAG = "CommonTipsView";
    private LinearLayout mTopArrowContainer;
    private LinearLayout mBottomArrowContainer;
    private TextView mTipsTextView;
    private Context mContext;

    private int mArrowGravity;
    private String mText ;
    private int mTextSize = 15;
    private int  mTipsColor;

    public CommonTipsView(Context context) {
        this(context,null);
    }

    public CommonTipsView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CommonTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_common_tips_view,this);
        initAttrs(attrs,defStyleAttr);
        initView();

    }

    private void initView(){
        mTopArrowContainer = (LinearLayout) findViewById(R.id.top_arrow_container);
        mBottomArrowContainer = (LinearLayout) findViewById(R.id.bottom_arrow_container);
        mTipsTextView = (TextView) findViewById(R.id.tips_text);

        mTipsTextView.setText(mText);

    }

    private void initAttrs(AttributeSet attrs,int defStyleAttr){
        TypedArray a = mContext.obtainStyledAttributes(attrs,R.styleable.CommonTipsView,defStyleAttr,0);
        mArrowGravity = a.getIndex(R.styleable.CommonTipsView_arrow_gravity);
        mText = a.getString(R.styleable.CommonTipsView_android_text);
        mTextSize = a.getDimensionPixelSize(R.styleable.CommonTipsView_android_textSize,mTextSize);
        mTipsColor = a.getColor(R.styleable.CommonTipsView_tips_color,mContext.getResources().getColor(R.color.common_tips_color));
        Logger.d(TAG,"gravity:"+Integer.toBinaryString(mArrowGravity));
    }

    public TextView getTipsTextView(){
        return  mTipsTextView;
    }


}
