package com.humorousz.uiutils.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.humorousz.commonutils.handler.HandlerCallback;
import com.humorousz.commonutils.handler.NormalHandler;
import com.humorousz.uiutils.R;
import com.humorousz.uiutils.helper.UIUtils;

/**
 * @author zhangzhiquan
 * @date 2018/2/7
 * 输入控件
 */

public class InputDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "InputDialog";
    private int MAX_CHAT_INPUT_LENGTH = 60;
    private static final int MSG_SHOW_KEYBOARD = 0x0004;

    private InputMethodManager mInputMethodManager;
    private EditText mEditText;
    private String mDefaultHint;
    private String mWillSendMsg;
    private Activity mActivity;
    private String mLastInput = "";
    private ViewGroup mSendBtn;
    private OnSendMessageListener mListener;
    private Handler mHandler = new NormalHandler(new HandlerCallback() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what){
                case MSG_SHOW_KEYBOARD:
                    if (mEditText != null) {
                        mEditText.requestFocus();
                    }
                    break;
                default:
                    break;
            }
        }
    });


    public InputDialog(Activity context,String defaultString) {
        this(context,defaultString,60);
    }

    public InputDialog(Activity context,String defaultString,int maxLength) {
        super(context, R.style.customDialog);
        mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mLastInput = mWillSendMsg = "";
        this.mDefaultHint = defaultString;
        this.mActivity = context;
        this.MAX_CHAT_INPUT_LENGTH = maxLength;
    }

    public void setOnSendMessageListener(OnSendMessageListener listener){
        mListener = listener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setWindow();
    }
    private void setWindow() {
        Window window = getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, getWindow().getAttributes().height);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.dimAmount = 0.0f;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_input_dialog);
        mSendBtn = findViewById(R.id.quizzes_send_btn);
        final TextView tvSumLeft =  findViewById(R.id.tv_comment_sum_left);
        mEditText = findViewById(R.id.et_comment_input);
        if (!TextUtils.isEmpty(mDefaultHint)) {
            mEditText.setHint(mDefaultHint);
        }
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mLastInput = mEditText.getText().toString();
                int numleft = MAX_CHAT_INPUT_LENGTH - UIUtils.getSpaceCount(mLastInput);
                if (numleft >= 0) {
                    tvSumLeft.setText(String.valueOf(numleft));
                    tvSumLeft.setTextColor(Color.parseColor("#99000000"));
                } else {
                    numleft = Math.abs(numleft);
                    tvSumLeft.setText("-" + numleft + "");
                    tvSumLeft.setTextColor(Color.RED);
                }
                if (UIUtils.getSpaceCount(mLastInput) > 0) {
                    mSendBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.input_edit_send_ready));
                } else {
                    mSendBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.input_edit_send_nomal));
                }
            }
        };
        mEditText.addTextChangedListener(watcher);
        if (!mLastInput.isEmpty()) {
            mEditText.setText(mLastInput);
        }
        mEditText.requestFocus();
        mHandler.sendEmptyMessageDelayed(MSG_SHOW_KEYBOARD, 500);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });


        int numleft = MAX_CHAT_INPUT_LENGTH - UIUtils.getSpaceCount(mLastInput);
        if (numleft >= 0) {
            tvSumLeft.setText(String.valueOf(numleft));
            tvSumLeft.setTextColor(Color.parseColor("#99000000"));
        } else {
            numleft = Math.abs(numleft);
            tvSumLeft.setText("-" + numleft);
            tvSumLeft.setTextColor(Color.RED);
        }
        mSendBtn.setOnClickListener(this);
    }

    private void sendMessage(){
        if(mListener == null){
            return;
        }
        if (MAX_CHAT_INPUT_LENGTH < UIUtils.getSpaceCount(mEditText.getText().toString())) {
            mListener.overMax(MAX_CHAT_INPUT_LENGTH,UIUtils.getSpaceCount(mEditText.getText().toString()));
            return;
        }
        mWillSendMsg = mEditText.getText().toString();
        mListener.sendMessage(mWillSendMsg);
        mLastInput = "";
        mEditText.setText("");
        dismiss();

    }

    @Override
    public void dismiss() {
        if (mInputMethodManager != null) {
            mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
        super.dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.quizzes_send_btn) {
            sendMessage();
        }
    }

    public interface OnSendMessageListener{
        /**
         * 发送消息
         * @param message
         */
        void sendMessage(String message);

        /**
         * 消息超过长度限制
         * @param max
         * @param real
         */
        void overMax(int max,int real);
    }
}
