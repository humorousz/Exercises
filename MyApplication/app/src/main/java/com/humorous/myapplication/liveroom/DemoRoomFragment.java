package com.humorous.myapplication.liveroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.humorous.myapplication.R;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorController;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorView;
import com.humorous.myapplication.liveroom.weidget.MinePopupWindow;
import com.humorousz.uiutils.helper.UIUtils;
import com.humorousz.uiutils.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/8/19.
 * @author zhangzhiquan
 */

public class DemoRoomFragment extends BaseFragment implements View.OnClickListener {
    ViewGroup mContainer;
    Button mSendBtn,mClearBtn,mMoveBtn,mPopBtn;
    IntoRoomAnimatorController mController;
    Animation inAnimation;
    int count = 0;
    boolean up = true;
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
        mMoveBtn = (Button) root.findViewById(R.id.moveBtn);
        mMoveBtn.setOnClickListener(this);
        mPopBtn = (Button) root.findViewById(R.id.popBtn);
        mPopBtn.setOnClickListener(this);
        mController = new IntoRoomAnimatorController(getContext(),mContainer);
        inAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.into_scale);



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
        }else if(v == mClearBtn) {
            mController.clear();
            count = 0;
        }else if(v == mMoveBtn){
            if(up){
                mContainer.setTranslationY(mContainer.getTranslationY() - UIUtils.dip2px(5));
            }else {
                mContainer.setTranslationY(mContainer.getTranslationY() + UIUtils.dip2px(5) );
            }

        }else if(v == mPopBtn){
            MinePopupWindow popupWindow = new MinePopupWindow(getActivity(),mPopBtn);
            popupWindow.showPopupWindow(mPopBtn);
        }

    }
}
