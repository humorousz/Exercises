package com.humorous.myapplication.liveroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorous.myapplication.frameAnimtor.widget.SelectGiftView;
import com.humorous.myapplication.frameAnimtor.widget.SendGiftPopupWindow;
import com.humorous.myapplication.liveroom.controller.GiftAnimationController;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorController;
import com.humorous.myapplication.liveroom.module.DefaultChatMessage;
import com.humorous.myapplication.liveroom.weidget.ChatBox;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.AnimatedImageView;

/**
 * Created by zhangzhiquan on 2017/8/19.
 * @author zhangzhiquan
 */

public class DemoRoomFragment extends BaseFragment implements View.OnClickListener,SelectGiftView.OnGiftItemClick {
    private static final String TAG = "DemoRoomFragment";
    private ViewGroup mContainer;
    private IntoRoomAnimatorController mController;
    private int userComeInCount = 0;
    private int messageCount = 0;
    private ChatBox mChatBox;
    private SendGiftPopupWindow mPop;
    private AnimatedImageView mWebPImage;
    private GiftAnimationController mGiftController;
    private View mRoot;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layoyt_fragment_demo_room,container,false);
    }

    @Override
    public void initView(View root) {
        mRoot = root;
        mContainer = root.findViewById(R.id.into_room_container);
        mController = new IntoRoomAnimatorController(getContext(),mContainer);
        mChatBox = root.findViewById(R.id.chat_box);
        mWebPImage = root.findViewById(R.id.webp_image);
        setClickListener(R.id.btn_come);
        setClickListener(R.id.btn_msg);
        setClickListener(R.id.image_gift);
        mGiftController = new GiftAnimationController(mWebPImage);
    }


    private void setClickListener(int id){
        mRoot.findViewById(id).setOnClickListener(this);
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
            case R.id.image_gift:
                openGiftBox();
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
        addNewMessage("humorousMan","我是第"+messageCount+"条测试消息");
    }

    public void addNewMessage(String user,String content){
        mChatBox.addNewChat(new DefaultChatMessage(user,content));
        messageCount++;
    }

    public void openGiftBox(){
        if(mPop == null){
            SelectGiftView view = new SelectGiftView(getContext());
            view.setOnGiftItemClickListener(this);
            ViewGroup group = getActivity().findViewById(android.R.id.content);
            View tiedView = group.getChildAt(0);
            mPop= new SendGiftPopupWindow(getContext(),view,tiedView);
        }
        mPop.show();
    }

    @Override
    public void onGiftItemClick(String path, String name) {
        mGiftController.addTask(path,name);
        addNewMessage("大土豪 humorous","赠送了一个"+name);
    }
}
