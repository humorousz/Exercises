package com.humorous.myapplication.liveroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorController;
import com.humorous.myapplication.liveroom.module.DefaultChatMessage;
import com.humorous.myapplication.liveroom.weidget.ChatBox;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/8/19.
 * @author zhangzhiquan
 */

public class DemoRoomFragment extends BaseFragment implements View.OnClickListener {
    private ViewGroup mContainer;
    private IntoRoomAnimatorController mController;
    private int userComeInCount = 0;
    private int messageCount = 0;
    private ChatBox mChatBox;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layoyt_fragment_demo_room,container,false);
    }

    @Override
    public void initView(View root) {
        mContainer = root.findViewById(R.id.into_room_container);
        mController = new IntoRoomAnimatorController(getContext(),mContainer);
        mChatBox = root.findViewById(R.id.chat_box);
        root.findViewById(R.id.btn_come).setOnClickListener(this);
        root.findViewById(R.id.btn_msg).setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.btn_come:
                userComeIn();
                break;
            case R.id.btn_msg:
                addNewMessage();
                break;
            default:
                break;
        }

    }

    public void userComeIn(){
        mController.addTask("humorous "+userComeInCount+" come");
        userComeInCount++;
    }

    private void clearCount(){
        mController.clear();
        userComeInCount = 0;
        messageCount = 0;
    }

    public void addNewMessage(){
        mChatBox.addNewChat(new DefaultChatMessage("humorousMan","我是第"+messageCount+"条测试消息"));
        messageCount++;
    }
}
