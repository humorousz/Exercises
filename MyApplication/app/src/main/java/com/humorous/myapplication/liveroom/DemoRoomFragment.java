package com.humorous.myapplication.liveroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorView;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/8/19.
 */

public class DemoRoomFragment extends BaseFragment {
    IntoRoomAnimatorView intoRoomAnimatorView;
    FrameLayout mContainer;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layoyt_fragment_demo_room,container,false);
    }

    @Override
    public void initView(View root) {
        mContainer = (FrameLayout) root.findViewById(R.id.into_room_container);
    }

    @Override
    public void onResume() {
        super.onResume();
        intoRoomAnimatorView = new IntoRoomAnimatorView(getContext());
        mContainer.addView(intoRoomAnimatorView);
        intoRoomAnimatorView.setData("张智全 进入了房间");
        mContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                intoRoomAnimatorView.setData("张智全 又进入了房间");
            }
        },6000);
    }
}
