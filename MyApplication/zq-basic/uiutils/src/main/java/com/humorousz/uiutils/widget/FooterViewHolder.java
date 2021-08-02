package com.humorousz.uiutils.widget;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.humorousz.uiutils.R;


/**
 * Created by zhangzhiquan on 2017/5/24.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    /**
     * 当前FooterView的状态
     */
    public enum FooterState {
        /**
         * 初始化
         */
        FOOTER_STATE_DEFAULT,
        /**
         * 当前有更多数据
         */
        FOOTER_STATE_HAS_NEXT_PAGE,
        /**
         * 无更多数据
         */
        FOOTER_STATE_NO_HAS_NEXT_PAGE,
        /**
         * 加载更多出现异常
         */
        FOOTER_STATE_LOAD_MORE_ERROR
    }

    FooterState mFooterState = FooterState.FOOTER_STATE_HAS_NEXT_PAGE;

    View footerView;
    TextView footerText;
    ProgressBar footerProgress;
    FooterCallBack mCallBack;

    public FooterViewHolder(View itemView) {
        super(itemView);
        footerView = itemView;
        footerView.setTag(FooterState.FOOTER_STATE_DEFAULT);
        footerText = (TextView) itemView.findViewById(R.id.textRecyclerFooter);
        footerProgress = (ProgressBar) itemView.findViewById(R.id.progressRecyclerFooter);
    }

    public void updataFooterState(FooterState state) {
        mFooterState = state;
        FooterState currentState = (FooterState) footerView.getTag();
        if (currentState != mFooterState) {
            switch (mFooterState) {
                case FOOTER_STATE_HAS_NEXT_PAGE:
                    footerProgress.setVisibility(View.VISIBLE);
                    footerText.setVisibility(View.VISIBLE);
                    footerText.setText("正在加载...");
                    footerView.setOnClickListener(null);

                    footerView.setTag(FooterState.FOOTER_STATE_HAS_NEXT_PAGE);
                    break;
                case FOOTER_STATE_LOAD_MORE_ERROR:
                    footerProgress.setVisibility(View.GONE);
                    footerText.setVisibility(View.VISIBLE);
                    footerText.setText("加载失败，点击重试");
                    footerView.setEnabled(true);
                    footerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCallBack.updateFooterView(v);
                        }
                    });
                    break;
                case FOOTER_STATE_NO_HAS_NEXT_PAGE:
                    footerProgress.setVisibility(View.GONE);
                    footerText.setVisibility(View.VISIBLE);
                    footerView.setOnClickListener(null);
                    footerText.setText("没有更多了");
                    footerView.setTag(FooterState.FOOTER_STATE_NO_HAS_NEXT_PAGE);
                    break;
            }
        }
    }


    public interface  FooterCallBack{
        void updateFooterView(View v);
    }
}
