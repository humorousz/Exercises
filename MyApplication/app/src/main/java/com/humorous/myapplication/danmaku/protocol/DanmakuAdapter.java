package com.humorous.myapplication.danmaku.protocol;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorous.myapplication.danmaku.controller.DanmaController;

/**
 * @author zhangzhiquan
 * on 2018/5/20
 */
public class DanmakuAdapter extends FrameLayout implements DanmakuProtocol {
    private DanmaController mController;
    public DanmakuAdapter(@NonNull Context context) {
        this(context,null);
    }

    public DanmakuAdapter(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public DanmakuAdapter(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_danmaku_adapter,this);
        mController = new DanmaController();
        mController.setDanmakuView(findViewById(R.id.danmaku_view));
    }

    @Override
    public void resume() {
        mController.resume();
    }

    @Override
    public void pause() {
        mController.pause();
    }

    @Override
    public void show() {
        mController.show();
    }

    @Override
    public void hide() {
        mController.hide();
    }

    @Override
    public void destroy() {
        mController.destroy();
    }

    @Override
    public void clean() {
        mController.clean();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void addDanmaku(IDanmakuData data) {
        mController.addDanmuWithoutIcon(data);
    }


}
