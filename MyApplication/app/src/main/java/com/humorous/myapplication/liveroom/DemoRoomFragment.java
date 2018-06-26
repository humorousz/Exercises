package com.humorous.myapplication.liveroom;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorous.myapplication.danmaku.Danmu;
import com.humorous.myapplication.danmaku.controller.DanmaController;
import com.humorous.myapplication.danmaku.protocol.DanmakuAdapter;
import com.humorous.myapplication.frameAnimtor.widget.SelectGiftView;
import com.humorous.myapplication.frameAnimtor.widget.SendGiftPopupWindow;
import com.humorous.myapplication.liveroom.controller.AutoController;
import com.humorous.myapplication.liveroom.controller.GiftAnimationController;
import com.humorous.myapplication.liveroom.intoRoom.IntoRoomAnimatorController;
import com.humorous.myapplication.liveroom.module.DefaultChatMessage;
import com.humorous.myapplication.liveroom.weidget.ChatBox;
import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.InputDialog;
import com.humrousz.sequence.AnimationImageView;

import java.util.Random;

/**
 * @author zhangzhiquan
 * @date 2017/8/19
 */

public class DemoRoomFragment extends BaseFragment implements View.OnClickListener,SelectGiftView.OnGiftItemClick,
        InputDialog.OnSendMessageListener,AutoController.OnAutoListener,SelectGiftView.OnSpaceClick {
    private static final String TAG = "DemoRoomFragment";
    private ViewGroup mContainer;
    private IntoRoomAnimatorController mController;
    private int userComeInCount = 0;
    private int messageCount = 0;
    private ChatBox mChatBox;
    private SendGiftPopupWindow mPop;
    private AnimationImageView mWebPImage;
    private GiftAnimationController mGiftController;
    private InputDialog mInputDialog;
    private AutoController mAutoController;
    private String[] mUserNames,mUserContent;
    private View mRoot;
    private DanmakuAdapter mDamakuView;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layoyt_fragment_demo_room,container,false);
    }

    @Override
    public void initView(View root) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            getActivity().findViewById(R.id.activity_container).setBackground(getResources().getDrawable(R.drawable.shader_bg));
        }else {
            getActivity().findViewById(R.id.activity_container).setBackgroundDrawable(getResources().getDrawable(R.drawable.shader_bg));
        }
        mRoot = root;
        mContainer = root.findViewById(R.id.into_room_container);
        mController = new IntoRoomAnimatorController(getContext(),mContainer);
        mChatBox = root.findViewById(R.id.chat_box);
        mWebPImage = root.findViewById(R.id.webp_image);
        setClickListener(R.id.btn_come);
        setClickListener(R.id.btn_msg);
        setClickListener(R.id.image_gift);
        setClickListener(R.id.tv_chat);
        setClickListener(R.id.btn_auto);
        setClickListener(R.id.btn_danmuku);
        mGiftController = new GiftAnimationController(mWebPImage);
        mAutoController = new AutoController(this);
        mUserNames = getResources().getStringArray(R.array.user_name);
        mUserContent = getResources().getStringArray(R.array.user_content);
        mDamakuView = root.findViewById(R.id.danmuku_view);
    }


    private void setClickListener(int id){
        mRoot.findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDamakuView.resume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mDamakuView.pause();
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
            case R.id.tv_chat:
                openInputDialog();
                break;
            case R.id.btn_auto:
                if(mAutoController.startAuto()){
                    mAutoController.stopAuto();
                }
                break;
            case R.id.btn_danmuku:
                addDanmaku(1);
                break;

            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAutoController.destroy();
        mDamakuView.destroy();
    }

    public void userComeIn(){
        mRoot.post(()->{
            mController.addTask("humorous "+userComeInCount+" come");
            userComeInCount++;
        });
    }

    private void clearCount(){
        mAutoController.stopAuto();
        mController.clear();
        userComeInCount = 0;
        messageCount = 0;
    }

    public void addNewMessage(){
        for(int i = 0 ;i<15;i++){
            addNewMessage("humorousMan","我是第"+messageCount+"条测试消息");
        }
    }

    public void addNewMessage(String user,String content){
        mChatBox.addNewChat(new DefaultChatMessage(user,content));
        messageCount++;
    }

    public void openGiftBox(){
        if(mPop == null){
            SelectGiftView view = new SelectGiftView(getContext());
            view.setOnGiftItemClickListener(this);
            view.setSpaceListener(this);
            ViewGroup group = getActivity().findViewById(android.R.id.content);
            View tiedView = group.getChildAt(0);
            mPop= new SendGiftPopupWindow(getContext(),view,tiedView);
        }
        mPop.show();
    }

    private void addDanmaku(int p){
        Random random = new Random();
        int r = random.nextInt(10);
        SpannableStringBuilder builder  = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("到此一游");
        if(p == 1){
            spannableString = new SpannableString("高优先级");
        }
        builder.append(spannableString);
//        for(int i = 0; i<r ; i++){
//            builder.append("1");
//        }

        Danmu danmu = new Danmu(123,false,"http://imgsrc.baidu.com/forum/w=580/sign=1588b7c5d739b6004dce0fbfd9503526/7bec54e736d12f2eb97e1a464dc2d56285356898.jpg","d",builder.toString(),p);
        if(mDamakuView != null){
            mDamakuView.addDanmaku(danmu);
        }
    }

    private void openInputDialog(){
        if(mInputDialog == null){
            mInputDialog = new InputDialog(getActivity(), "快来聊两句吧");
            mInputDialog.setOnSendMessageListener(this);
        }
        mInputDialog.show();
    }

    @Override
    public void onGiftItemClick(String path, String name) {
        mGiftController.addTask(path,name);
        addNewMessage("大土豪 humorous","赠送了一个"+name);
    }

    /**
     * 发送消息
     *
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        addNewMessage("humorousz",message);
    }

    /**
     * 消息超过长度限制
     *
     * @param max
     * @param real
     */
    @Override
    public void overMax(int max, int real) {
        ToastUtil.showToast(getContext(),"文字超出长度限制了 哈尼！");
    }


    private Random mNameRandom;
    private Random mContentRandom;
    /**
     * 自动发言
     * 具体如何发言，可自行实现
     */
    @Override
    public void autoSendMessage() {
        if(mNameRandom == null){
            mNameRandom = new Random();
            mContentRandom = new Random();
        }
        int mNameIndex = mNameRandom.nextInt(mUserNames.length);
        int mContentIndex = mContentRandom.nextInt(mUserContent.length);
        addNewMessage(mUserNames[mNameIndex],mUserContent[mContentIndex]);
        addDanmaku(0);
    }

    /**
     * 自动用户进场
     */
    @Override
    public void autoComeInUser() {
        userComeIn();
    }

    @Override
    public void clickGiftPanelSpace() {
        if(mPop != null){
            mPop.dismiss();
        }
    }
}
