package com.example.zhangzhiquan.myapplication.fragmentDemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhangzhiquan.myapplication.R;
import com.example.zhangzhiquan.myapplication.fragmentDemo.fragment.FragmentOne;
import com.example.zhangzhiquan.myapplication.fragmentDemo.fragment.FragmentTwo;
import com.example.zhangzhiquan.myapplication.utils.MyLog;

public class FragmentContainerActivity extends FragmentActivity {


    private RadioGroup mRadioGroup;
    private FragmentManager mFragmentManager;
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private int curId = R.id.radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        mFragmentManager = getSupportFragmentManager();

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(curId == checkedId)
                    return;
                curId = checkedId;
                if(checkedId == R.id.radioButton){
                    onBtn1Click();
                }else {
                    onBtn2Click();
                }
            }
        });
        ((RadioButton)(mRadioGroup.findViewById(R.id.radioButton))).setChecked(true);
        FragmentTransaction tx =  mFragmentManager.beginTransaction();
        tx.add(R.id.container,fragmentOne,"one");
        tx.commit();
        MyLog.e("FFF act",new Exception());
    }


    private void onBtn2Click() {
        FragmentTransaction tx = mFragmentManager.beginTransaction();
        tx.replace(R.id.container, fragmentTwo, "two");
        tx.hide(fragmentOne);
        tx.addToBackStack(null);
        tx.commit();
    }

    private void onBtn1Click() {
        FragmentTransaction tx = mFragmentManager.beginTransaction();
        tx.replace(R.id.container, fragmentOne, "one");
        tx.hide(fragmentTwo);
        tx.addToBackStack(null);
        tx.commit();
    }
}
