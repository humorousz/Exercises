package com.humorous.myapplication.vectorDrawableTest;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/10/29.
 */

public class VectorTestFragment extends BaseFragment implements GuardView.GuardStateListener {
    private Button button;
    private GuardView mGuardView;
    private  FrameLayout layout;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_vector,container,false);
    }

    @Override
    public void initView(final View root) {
        layout = (FrameLayout) root;
        button = (Button) root.findViewById(R.id.guard_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addGuardView();
            }
        });
    }

    private void addGuardView(){
        mGuardView = new GuardView(getContext());
        mGuardView.setGuardStateListener(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        layout.addView(mGuardView);
    }
    @Override
    public void onStartAnim() {

    }

    @Override
    public void onEndAnim() {
        layout.removeView(mGuardView);
        mGuardView = null;
    }
}
