package com.humorous.myapplication.vectorDrawableTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;

/**
 * Created by zhangzhiquan on 2017/10/30.
 */

public class GuardView extends RelativeLayout {
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
        setAllViewGone();
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

    private void setAllViewGone(){
        int count = getChildCount();
        for(int i = 0; i < count;i++){
            getChildAt(i).setVisibility(INVISIBLE);
        }
    }
}
