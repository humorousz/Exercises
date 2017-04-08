package com.example.zhangzhiquan.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.zhangzhiquan.myapplication.adapter.ParentAdapter;
import com.example.zhangzhiquan.myapplication.fragmentDemo.FragmentContainerActivity;
import com.example.zhangzhiquan.myapplication.lifeCyclerTest.LifeActivity1;
import com.example.zhangzhiquan.myapplication.widget.MyTextView;
import com.example.zhangzhiquan.myapplication.widget.OuterRecyclerView;

public class MainActivity extends FragmentActivity {
    //    FrameLayout mContainer;
    private static final String TAG = "MainActivity";
    public static int count = 0;
    private MyTextView mTextView;
    private OuterRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mContainer = (FrameLayout) findViewById(R.id.container);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container,new AntFragment())
//        .commit();
        mTextView = (MyTextView) findViewById(R.id.myTextView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MRZ","x:"+mTextView.getX());
                Log.d("MRZ","y:"+mTextView.getY());
                mTextView.scrollTo(100,100);
                Log.d("MRZ","after x:"+mTextView.getX());
                Log.d("MRZ","after y:"+mTextView.getY());
                startActivity(new Intent(MainActivity.this,FragmentContainerActivity.class));
            }
        });
        recyclerView = (OuterRecyclerView) findViewById(R.id.outer_recycler);
        ParentAdapter adapter = new ParentAdapter();
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter.setContainer(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG,"dispatchTouchEvent ACTION_MOVE");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG,"onTouchEvent ACTION_MOVE");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
