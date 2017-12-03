package com.humorous.myapplication.shareElement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.config.factory.TestFragmentFactory;
import com.humorous.myapplication.container.ContainerActivity;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/12/3.
 */

public class ShareElementFragmentA extends BaseFragment implements View.OnClickListener {
    private ImageView mShareImg;
    private Button mClickBtn;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_share_a,container,false);
    }

    @Override
    public void initView(View root) {
        mShareImg = root.findViewById(R.id.share_a_img);
        mClickBtn = root.findViewById(R.id.share_a_btn);
        mClickBtn.setOnClickListener(this);
        mShareImg.post(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Intent intent =  new Intent(getActivity(), ContainerActivity.class);
            intent.putExtra(ContainerActivity.HAS_TITLE,true);
            intent.putExtra(ContainerActivity.FRAMGNET_TYPE, TestFragmentFactory.TYPE.SHARE_ELEMENT_B);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create((View)mClickBtn, "share_btn"),Pair.create((View)mShareImg,"share_img"));
            getActivity().startActivity(intent,options.toBundle());
//            getActivity().startActivity(intent);
        }
    }
}
