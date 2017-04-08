package com.example.zhangzhiquan.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangzhiquan.myapplication.widget.AntView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AntFragment extends Fragment {


    AntView mAntView;
    public AntFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ant, container, false);
        mAntView = (AntView) root.findViewById(R.id.ant_view);
        mAntView.setSesameValues(500);
        mAntView.startAnim();
        return root;
    }

}
