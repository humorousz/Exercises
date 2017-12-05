package com.humorous.myapplication.liveroom;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.liveroom.weidget.VerticalTextViewSwitcher;
import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.helper.UIUtils;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.CommonTipsView;

import java.util.ArrayList;

/**
 * Created by zhangzhiquan on 2017/9/26.
 */

public class DemoUIFragment extends BaseFragment implements View.OnClickListener{
    private Button lbtn,mbtn,rbtn;
    private EditText editText;
    private LinearLayout mTipsContainer;
    private CommonTipsView commonTipsView;
    private ImageView mImage;
    private VerticalTextViewSwitcher mTextSwitcher;
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
        mImage = (ImageView) root.findViewById(R.id.image);
        mTipsContainer = (LinearLayout) root.findViewById(R.id.commonTipsView);
        mImage.setImageResource(R.drawable.light);
        AnimationDrawable anim = (AnimationDrawable) mImage.getDrawable();
        anim.start();
        lbtn.setOnClickListener(this);
        mbtn.setOnClickListener(this);
        rbtn.setOnClickListener(this);
        initSwitcher(root);
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
                commonTipsView.setArrowGravity(Gravity.BOTTOM|Gravity.LEFT);
                break;
            case 2:
                commonTipsView.setArrowGravity(Gravity.BOTTOM|Gravity.CENTER);
                break;
            case 3:
                commonTipsView.setArrowGravity(Gravity.BOTTOM|Gravity.RIGHT);
                break;
        }
        commonTipsView.setText(editText.getText().toString());

    }

    private void initSwitcher(View root){
        mTextSwitcher = root.findViewById(R.id.verticalTextView);
        mTextSwitcher.setOnMakeViewListener(new VerticalTextViewSwitcher.OnMakeView() {
            @Override
            public TextView makeView() {
                TextView t = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.switcher_textview,null);
                Drawable drawable = getResources().getDrawable(R.drawable.go_to);
                drawable.setBounds(0,0,drawable.getIntrinsicWidth() * 2 / 3,drawable.getIntrinsicHeight() * 2 / 3);
                t.setCompoundDrawables(null,null,drawable,null);
                return t;
            }
        });
        final ArrayList<CharSequence> list = new ArrayList<>();
        list.add("星星排行榜a");
        list.add("星星排行榜b");
        list.add("榜c");
        list.add("星榜d");
        list.add("星星排行榜e");
        list.add("星榜f");
        list.add("星星排行榜g");
        list.add("星星排行榜h");
        mTextSwitcher.setOnItemClickListener(new VerticalTextViewSwitcher.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.showToast(getContext(),list.get(position));
            }
        });
        mTextSwitcher.setTextStillTime(2000);//设置停留时长间隔
        mTextSwitcher.setAnimTime(300);//设置进入和退出的时间间隔
        mTextSwitcher.setTextList(list);
        mTextSwitcher.startAutoScroll();
    }
}
