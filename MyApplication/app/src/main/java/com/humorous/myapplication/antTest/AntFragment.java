package com.humorous.myapplication.antTest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorous.myapplication.antTest.AntView;
import com.humorousz.uiutils.view.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AntFragment extends BaseFragment {

    private static final String TAG = "AntFragment";

    AntView mAntView;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ant, container, false);
    }

    @Override
    public void initView(View root) {
        mAntView = (AntView) root.findViewById(R.id.ant_view);
        mAntView.setSesameValues(500);
        mAntView.startAnim();
    }

    @Override
    public String getLogTitle() {
        return TAG;
    }

}
