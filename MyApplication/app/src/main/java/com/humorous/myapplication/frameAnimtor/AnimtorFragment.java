package com.humorous.myapplication.frameAnimtor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.humorous.myapplication.R;
import com.humorous.myapplication.frameAnimtor.webp.BitMapActor;
import com.humorousz.uiutils.view.BaseFragment;


/**
 * Created by zhangzhiquan on 2017/8/2.
 */

public class AnimtorFragment extends BaseFragment implements  BaseActor.AnimStateListener {
    private static final String TAG = "AnimtorFragment";
    private LinearLayout root;
    private BaseActor mActor;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_animtor_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        this.root = (LinearLayout) root.findViewById(R.id.animtor_linear);
        mActor  = new BitMapActor(getContext(),"f3/car.webp");
        mActor.setAnimStateListener(this);
        this.root.addView(mActor);
    }

    private void clearAnim(){
        if(mActor != null){
            this.root.removeView(mActor);
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

}
