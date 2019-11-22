package com.humorous.myapplication.home.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.helper.UIUtils;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;
    private int mPosition;
    private OnItemClickListener mListener;

    public HomeHolder(View itemView) {
        this(itemView, null);
    }

    public HomeHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.home_item_text);
        mTextView.setOnClickListener(this);
        setOnItemClickListener(listener);
        resetHeight(itemView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void bindData(String title,int position) {
        mPosition = position;
        if(!TextUtils.isEmpty(title)){
            mTextView.setText(title);
        }else {
            mTextView.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(mPosition);
        }
    }

    private void resetHeight(View view){
        ViewGroup.LayoutParams paras = view.getLayoutParams();
        paras.height = UIUtils.getScreenWidth()/2;
        view.setLayoutParams(paras);
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
