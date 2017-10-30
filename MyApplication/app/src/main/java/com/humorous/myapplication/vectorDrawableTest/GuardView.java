package com.humorous.myapplication.vectorDrawableTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.humorous.myapplication.R;

/**
 * Created by zhangzhiquan on 2017/10/30.
 */

public class GuardView extends RelativeLayout {
    private ViewGroup root;
    private Context mContext;
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

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.layout_gurad_view,this,true);
    }
}
