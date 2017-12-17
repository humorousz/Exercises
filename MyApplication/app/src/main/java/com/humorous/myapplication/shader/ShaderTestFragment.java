package com.humorous.myapplication.shader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.shader.widget.MaskLoadingView;
import com.humorousz.uiutils.helper.ImageLoaderHelper;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/12/17.
 * @author zhangzhiquan
 */

public class ShaderTestFragment  extends BaseFragment{
    private MaskLoadingView mTestMask,mImageMask;
    private ImageView mImage;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_test_shader,container,false);
    }

    @Override
    public void initView(View root) {
        mTestMask = root.findViewById(R.id.text_mask);
        int[] colors = new int[]{Color.TRANSPARENT, Color.parseColor("#02ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#02ffffff"), Color.TRANSPARENT};
        float[] position = new float[]{0.3f, 0.4f, 0.5f, 0.6f, 0.7f};
        mTestMask.setColors(colors);
        mTestMask.setPositions(position);

        mImage = root.findViewById(R.id.shade_image);
        ImageLoaderHelper.displayCircleImage("drawable://"+R.drawable.sssssss,mImage);
    }
}
