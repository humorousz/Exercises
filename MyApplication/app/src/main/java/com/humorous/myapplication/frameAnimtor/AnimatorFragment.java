package com.humorous.myapplication.frameAnimtor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.frameAnimtor.webp.BitMapActor;
import com.humorous.myapplication.frameAnimtor.widget.SelectGiftView;
import com.humorous.myapplication.frameAnimtor.widget.SendGiftPopupWindow;
import com.humorousz.uiutils.view.BaseFragment;
import com.humrousz.sequence.AnimationImageView;
import java.util.LinkedList;
import java.util.Queue;


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
    private TextView mGiftCount;
    private Queue<Pair<String,String>> paths;
    private boolean isRunning;
    private boolean isDestoryView = false;
    private AnimationImageView webpImage;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_animtor_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        isDestoryView = false;
        this.container = (FrameLayout) root.findViewById(R.id.anmi_container);
        this.mSendBtn = (Button) root.findViewById(R.id.sendBtn);
        this.mGiftCount = (TextView) root.findViewById(R.id.tv_gift_count);
        this.webpImage = root.findViewById(R.id.webp_image);
        mSendBtn.setOnClickListener(this);
        paths = new LinkedList<>();
    }


    private void addTask(String path,String name){
        paths.offer(new Pair<>(path,name));
        dumpPath();
        takeTask();
    }

    private void takeTask(){
        if(!isRunning && ! paths.isEmpty()){
            startAnim(paths.poll().first);
        }
        dumpPath();
    }

    private void dumpPath(){
        StringBuilder sb = new StringBuilder();
        sb.append("当前礼物队列:"+"\n");
        for(Pair<String,String> pair:paths){
            sb.append(pair.second + "\n");
        }
        mGiftCount.setText(sb.toString());
    }

    private void startAnim(String path){
        isRunning = true;
        mActor = new BitMapActor(getContext(),path);
        mActor.setAnimStateListener(this);
        container.addView(mActor);
    }

    private void clearAnim(){
        isRunning = false;
        if(mActor != null){
            container.removeView(mActor);
            mActor = null;
        }
        if(!isDestoryView){
            takeTask();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestoryView = true;
        if(paths != null){
            paths.clear();
        }
        if(mPop != null){
            mPop.dismiss();
            mPop = null;
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
    public void onGiftItemClick(String path,String name) {
        addTask(path,name);
    }
}
