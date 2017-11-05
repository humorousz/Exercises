package com.humorous.myapplication.vectorDrawableTest;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/10/29.
 */

public class VectorTestFragment extends BaseFragment {
    private Button button,clearBtn;
    private GuardController mController;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_vector,container,false);
    }

    @Override
    public void initView(final View root) {
        button = (Button) root.findViewById(R.id.guard_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardMessage msg = new GuardMessage("a","d",GuardMessage.GUARD_MONTH);
                mController.addTask(msg);
            }
        });
        clearBtn = (Button) root.findViewById(R.id.guard_clear_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.clear();
            }
        });
        ViewGroup viewGroup = (ViewGroup) root.findViewById(R.id.guard_container);
        mController = new GuardController(viewGroup);
    }
}
