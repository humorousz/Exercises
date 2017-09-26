package com.humorous.myapplication.liveroom;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.CommonTipsView;

/**
 * Created by zhangzhiquan on 2017/9/26.
 */

public class DemoUIFragment extends BaseFragment implements View.OnClickListener{
    private Button lbtn,mbtn,rbtn;
    private EditText editText;
    private LinearLayout mTipsContainer;
    private CommonTipsView commonTipsView;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_demo_ui,container,false);
    }

    @Override
    public void initView(View root) {
        lbtn = (Button) root.findViewById(R.id.left_btn);
        mbtn = (Button) root.findViewById(R.id.middle_btn);
        rbtn = (Button) root.findViewById(R.id.right_btn);
        editText = (EditText) root.findViewById(R.id.tip_edit);
        mTipsContainer = (LinearLayout) root.findViewById(R.id.commonTipsView);
        lbtn.setOnClickListener(this);
        mbtn.setOnClickListener(this);
        rbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == lbtn){
            showTips(1);
        }else if(v == mbtn){
            showTips(2);
        }else if(v == rbtn){
            showTips(3);
        }
    }



    private void showTips(int gravity){
        if(commonTipsView == null){
            commonTipsView = new CommonTipsView(getContext());
            commonTipsView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mTipsContainer.addView(commonTipsView);
        }
        switch (gravity){
            case 1:
                commonTipsView.setArrowGravity(Gravity.LEFT);
                break;
            case 2:
                commonTipsView.setArrowGravity(Gravity.CENTER);
                break;
            case 3:
                commonTipsView.setArrowGravity(Gravity.RIGHT);
                break;
        }
        if(editText != null){
            commonTipsView.setText(editText.getText());
        }

    }
}
