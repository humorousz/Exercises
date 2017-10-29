package com.humorous.myapplication.vectorDrawableTest;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/10/29.
 */

public class VectorTestFragment extends BaseFragment {
    private ImageView mImageView;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_vector,container,false);
    }

    @Override
    public void initView(View root) {
        mImageView = (ImageView) root.findViewById(R.id.vector_image);
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT >= 21){
                    AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.vaaa);
                    mImageView.setImageDrawable(drawable);
                    if(drawable != null){
                        drawable.start();
                    }
                }


            }
        });
    }
}
