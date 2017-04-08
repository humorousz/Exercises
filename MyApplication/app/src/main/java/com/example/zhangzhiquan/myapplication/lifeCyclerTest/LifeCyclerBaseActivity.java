package com.example.zhangzhiquan.myapplication.lifeCyclerTest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zhangzhiquan.myapplication.R;

public abstract class LifeCyclerBaseActivity extends Activity {

    TextView mTextView;
    String TAG = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        mTextView = (TextView) findViewById(R.id.text_view);
        mTextView.setText(getTitleString());
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextClick();
            }
        });
        TAG = getTitleString();
        Log.e(TAG,TAG+":onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,TAG+":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,TAG+":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,TAG+":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,TAG+":onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,TAG+":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,TAG+":onDestory");
    }

    abstract String getTitleString();

    protected void onTextClick(){

    }

}
