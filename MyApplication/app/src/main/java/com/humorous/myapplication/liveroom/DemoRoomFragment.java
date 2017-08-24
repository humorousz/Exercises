package com.humorous.myapplication.liveroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorController;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorView;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/8/19.
 */

public class DemoRoomFragment extends BaseFragment implements View.OnClickListener {
    IntoRoomAnimatorView intoRoomAnimatorView;
    ViewGroup mContainer;
    Button mSendBtn,mClearBtn;
    IntoRoomAnimatorController mController;
    int count = 0;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layoyt_fragment_demo_room,container,false);
    }

    @Override
    public void initView(View root) {
        mContainer = (ViewGroup) root.findViewById(R.id.into_room_container);
        mSendBtn = (Button) root.findViewById(R.id.sendBtn);
        mSendBtn.setOnClickListener(this);
        mClearBtn = (Button) root.findViewById(R.id.clearBtn);
        mClearBtn.setOnClickListener(this);
        mController = new IntoRoomAnimatorController(getContext(),mContainer);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mController != null){
            mController.release();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mSendBtn){
            mController.addTask("humorous "+count+" come");
            count++;
        }else {
            mController.clear();
            count = 0;
        }

    }
}
