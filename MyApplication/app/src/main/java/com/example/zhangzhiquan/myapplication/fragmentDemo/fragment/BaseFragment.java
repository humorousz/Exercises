package com.example.zhangzhiquan.myapplication.fragmentDemo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangzhiquan.myapplication.R;
import com.example.zhangzhiquan.myapplication.utils.MyLog;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {



    TextView mContent;
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyLog.e(getContent(),new Exception());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.e(getContent(),new Exception());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        mContent = (TextView) root.findViewById(R.id.textView);
        mContent.setText(getContent());
        MyLog.e(getContent(),new Exception());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyLog.e(getContent(),new Exception());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLog.e(getContent(),new Exception());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.e(getContent(),new Exception());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLog.e(getContent(),new Exception());
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    protected abstract String getContent();

}
