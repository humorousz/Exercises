package com.humorousz.uiutils.view;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * @author zhangzhiquan
 * on 2018/5/19
 */
public class BaseActivity extends AppCompatActivity {
    public static final String TITLE = "title";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
