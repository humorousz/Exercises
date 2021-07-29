package com.humorusz.practice.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.humorousz.uiutils.view.BaseFragment;
import com.humorusz.practice.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * @author zhangzhiquan
 * @date 2018/6/4
 */
public class ZxingFragment extends BaseFragment {
    private static int REQUEST_CODE = 1001;
    private Button mSaoYiSao;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_zxing_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        mSaoYiSao = root.findViewById(R.id.btn_sao_yi_sao);
        mSaoYiSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                //是否播放扫描声音 默认为true
                config.setPlayBeep(true);
                //是否震动  默认为true
                config.setShake(true);
                //是否扫描条形码 默认为true
                config.setDecodeBarCode(false);
                //是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                config.setFullScreenScan(true);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    String result = bundle.getString(Constant.CODED_CONTENT);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (requestCode == RESULT_CANCELED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
