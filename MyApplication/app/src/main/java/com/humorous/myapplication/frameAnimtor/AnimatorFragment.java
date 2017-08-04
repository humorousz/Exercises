package com.humorous.myapplication.frameAnimtor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorous.myapplication.frameAnimtor.webp.BitMapActor;
import com.humorous.myapplication.frameAnimtor.widget.SelectGiftView;
import com.humorous.myapplication.frameAnimtor.widget.SendGiftPopupWindow;
import com.humorousz.uiutils.view.BaseFragment;


/**
 * Created by zhangzhiquan on 2017/8/2.
 */

public class AnimatorFragment extends BaseFragment implements  BaseActor.AnimStateListener
        ,View.OnClickListener,SelectGiftView.OnGiftItemClick {
    private static final String TAG = "AnimatorFragment";
    private FrameLayout container;
    private BaseActor mActor;
    private Button mSendBtn;
    private SendGiftPopupWindow mPop;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_animtor_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        this.container = (FrameLayout) root.findViewById(R.id.anmi_container);
        this.mSendBtn = (Button) root.findViewById(R.id.sendBtn);
        mSendBtn.setOnClickListener(this);
    }

    private void startAnim(String path){
        mActor = new BitMapActor(getContext(),path);
        mActor.setAnimStateListener(this);
        container.addView(mActor);
    }

    private void clearAnim(){
        if(mActor != null){
            container.removeView(mActor);
            mActor = null;
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BaseActor.State_Start:
                    break;
                case BaseActor.State_End:
                    clearAnim();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onStartAnim() {

    }

    @Override
    public void onEndAnim() {
        mHandler.sendEmptyMessage(BaseActor.State_End);
    }

    @Override
    public void onClick(View v) {
        if(mPop == null){
            SelectGiftView view = new SelectGiftView(getContext());
            view.setOnGiftItemClickListener(this);
            ViewGroup group = (ViewGroup) getActivity().findViewById(android.R.id.content);
            View tiedView = group.getChildAt(0);
            mPop= new SendGiftPopupWindow(getContext(),view,tiedView);
        }
        mPop.show();
    }

    @Override
    public void onGiftItemClick(String path) {
        startAnim(path);
    }
}
