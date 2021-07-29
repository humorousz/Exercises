package com.humorusz.practice.shader;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humorusz.practice.R;
import com.humorusz.practice.shader.widget.GradinetDrawable;
import com.humorusz.practice.shader.widget.MaskLoadingView;
import com.humorousz.uiutils.helper.ImageLoaderHelper;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/12/17.
 * @author zhangzhiquan
 */

public class ShaderTestFragment  extends BaseFragment{
    private MaskLoadingView mTestMask;
    private ImageView mImage;
    private ImageView gImage;
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
        int[] image_colors = new int[]{Color.TRANSPARENT, Color.parseColor("#02ffffff"), Color.parseColor("#ffffff"), Color.parseColor("#02ffffff"), Color.TRANSPARENT};
        float[] image_position = new float[]{0.4f, 0.45f, 0.5f, 0.55f, 0.6f};
//        mImageMask.setColors(image_colors);
//        mImageMask.setPositions(image_position);
//        mImageMask.setCallbackListener(new MaskLoadingView.MaskLoadingCallBack() {
//            @Override
//            public Shader getShader(Canvas canvas) {
//                Shader shader = new LinearGradient(0, 0, canvas.getWidth()/2,0, image_colors, null, Shader.TileMode.REPEAT);
//                return shader;
//            }
//        });

        gImage = root.findViewById(R.id.gradientImage);
        gImage.setImageDrawable(new GradinetDrawable(getContext(), BitmapFactory.decodeResource(getResources(),R.drawable.sssssss)));
        root.findViewById(R.id.text_shader).getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_ATOP);
        root.findViewById(R.id.text_shader).getBackground().setAlpha(Color.alpha(Color.parseColor("#55ff0000")));

    }
}
