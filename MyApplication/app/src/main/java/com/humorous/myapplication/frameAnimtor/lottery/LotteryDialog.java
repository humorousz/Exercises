package com.humorous.myapplication.frameAnimtor.lottery;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/10/6.
 */

public class LotteryDialog extends DialogFragment {
    ViewGroup mContainer;
    MineLotteryViewController mController;
    View root;
    View lotteryView;

    public LotteryDialog(){
        int style=DialogFragment.STYLE_NO_TITLE;
        int theme=android.R.style.Theme_Translucent;
        setStyle(style, theme);
    }

    public void setController(MineLotteryViewController controller){
        mController = controller;
    }
    public void setLotteryView(View v){
        lotteryView = v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.layout_lottery_dialog,container,false);
        mContainer = (ViewGroup)root.findViewById(R.id.lottery_container);
        mContainer.addView(lotteryView);
        return root;
    }

    public void setController(){
        MineLotteryData data = new MineLotteryData(1,100,10,1,1000);
        MineLotteryData data2 = new MineLotteryData(1,100,100,1,1000);
        List<MineLotteryData> list = new ArrayList<>();
        list.add(data);
        list.add(data2);
        mController.addTask(list);
    }

}
