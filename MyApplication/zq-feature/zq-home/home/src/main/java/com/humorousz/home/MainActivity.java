package com.humorousz.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.plugin.ZQPluginManager;
import com.example.plugin.biz.ZXingPlugIn;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.home.config.api.Api;
import com.humorousz.home.config.router.Router;
import com.humorousz.home.home.HomeFragment;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.view.ImmerseActivity;

/**
 * MainActivity
 *
 * @author zhangzhiquan
 */
public class MainActivity extends ImmerseActivity {
  private static final String TAG = "MainActivity";
  private static final int REQUEST_CODE = 1001;
  private FrameLayout mContainer;
  private BaseFragment mFragment;
  private Toolbar mToolBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      String fragmentTag = "android:support:fragments";
      savedInstanceState.remove(fragmentTag);
    }
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initToolBar();
    mFragment = HomeFragment.newInstance(Api.SECOND_MENU.MAIN);
    mContainer = findViewById(R.id.container);
    FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
    tr.add(R.id.container, mFragment);
    tr.commit();
    Logger.d(TAG, Thread.currentThread().hashCode());
  }

  private void initToolBar() {
    mToolBar = findViewById(R.id.toolbar);
    TextView title = mToolBar.findViewById(R.id.title);
    title.setText(R.string.main_title);
    title.setSelected(true);
    mToolBar.setTitle("");
    setSupportActionBar(mToolBar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    mToolBar.findViewById(R.id.btn_sao_yi_sao).setOnClickListener((v) -> {
      ZQPluginManager.getInstance().getPlugin(ZXingPlugIn.class).openZXingPage(MainActivity.this);
    });
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    Logger.d(TAG, "onRestore");
  }

  @Override
  public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    Logger.d(TAG, "onSave");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Logger.d(TAG, "onRestart");
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onRestoreInstanceState(savedInstanceState, persistentState);
    Logger.d(TAG, "onRestoreInstanceState PersistableBundle");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Logger.d(TAG, "onSaveInstanceState");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Logger.d(TAG, "onStop");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Logger.d(TAG, "onPause");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Logger.d(TAG, "onDestroy");
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE) {
      //处理扫描结果（在界面上显示）
      if (null != data) {
        Bundle bundle = data.getExtras();
        if (bundle == null) {
          return;
        }
        if (resultCode == RESULT_OK) {
          String result =
              ZQPluginManager.getInstance().getPlugin(ZXingPlugIn.class).getResult(bundle);
          Router.open(this, result);
        }
      }
    }
  }

  @Override
  protected boolean isSupportSwipeBack() {
    return false;
  }
}
