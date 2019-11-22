package com.humorous.myapplication.frameAnimtor.lottery;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;

/**
 * Created by zhangzhiquan on 2017/10/6.
 */

public class LotteryDialog extends DialogFragment {
    ViewGroup mContainer;
    View root;
    View lotteryView;

    public LotteryDialog(){
        int style=DialogFragment.STYLE_NO_TITLE;
        int theme=android.R.style.Theme_Translucent;
        setStyle(style, theme);
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


}
