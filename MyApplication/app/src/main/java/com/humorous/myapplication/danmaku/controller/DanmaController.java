package com.humorous.myapplication.danmaku.controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.humorous.myapplication.danmaku.CenteredImageSpan;
import com.humorous.myapplication.danmaku.DanmuConfig;
import com.humorous.myapplication.danmaku.protocol.IDanmakuData;
import com.humorousz.uiutils.helper.ImageLoaderHelper;
import com.humorousz.uiutils.helper.ImageLoadingListener;
import com.humorousz.uiutils.helper.UIUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

/**
 * @author zhangzhiquan
 * on 2018/5/19
 */
public class DanmaController {
    private static final String TAG = "DanmaController";

    /**
     * 弹幕显示的时间(如果是list的话，会 * i)，记得加上mDanmakuView.getCurrentTime()
     */
    private static final long ADD_DANMU_TIME = 1200;

    private static final int DURATION_LONG = 7000;
    private static final int DURATION_SHORT = 2000;
    private static final int MAX_SIZE = 6;

    private int DANMU_BG = 0x4D000000;

    private int BITMAP_WIDTH = 23;//头像的大小
    private int BITMAP_HEIGHT = 23;
    //弹幕字体的大小
    private int DANMU_TEXT_SIZE = 13;
    //emoji的大小
    private int EMOJI_SIZE = 12;

    //这两个用来控制两行弹幕之间的间距
    private int DANMU_PADDING = 8;
    private int DANMU_PADDING_INNER = 3;
    //圆角半径
    private int DANMU_RADIUS = 15;

    private final int mGoodUserId = 1;
    private final int mMyUserId = 2;

    private IDanmakuView mDanmakuView;
    private DanmakuContext mDanmakuContext;
    //记录上次发弹幕的时间 两次弹幕间隔太短 加间隔时间
    private long lastDanmuTime;
    private Duration mLongDuration, mShortDuration;

    private int[] mColors = new int[]{Color.WHITE,Color.BLACK,Color.RED,Color.BLUE,Color.MAGENTA,Color.GREEN,Color.YELLOW};
    private Random mColorRandom;

    public DanmaController() {
        setSize();
        initDanmuConfig();

    }


    public DanmaController(DanmuConfig config) {
        if (config.textSize != 0) {
            DANMU_TEXT_SIZE = config.textSize;
        }
        if (config.danmuBg != 0) {
            DANMU_BG = config.danmuBg;
        }

        if (config.bgRadius != 0) {
            DANMU_RADIUS = config.bgRadius;
        }
        setSize();
        initDanmuConfig();

    }

    /**
     * 对数值进行转换，适配手机，必须在初始化之前，否则有些数据不会起作用
     */
    private void setSize() {
        BITMAP_WIDTH = UIUtils.dip2px(BITMAP_HEIGHT);
        BITMAP_HEIGHT = UIUtils.dip2px(BITMAP_HEIGHT);
        EMOJI_SIZE = UIUtils.dip2px(EMOJI_SIZE);
        DANMU_PADDING = UIUtils.dip2px(DANMU_PADDING);
        DANMU_PADDING_INNER = UIUtils.dip2px(DANMU_PADDING_INNER);
        DANMU_RADIUS = UIUtils.dip2px(DANMU_RADIUS);
        DANMU_TEXT_SIZE = UIUtils.dip2px(DANMU_TEXT_SIZE);
    }

    /**
     * 初始化配置
     */
    private void initDanmuConfig() {
        // 设置最大显示行数
        Map<Integer, Integer> maxLinesPair = new HashMap<>(16);
        // 滚动弹幕最大显示2行
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 3);
        // 设置是否禁止重叠
        Map<Integer, Boolean> overlappingEnablePair = new HashMap<>(16);
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, false);

        mDanmakuContext = DanmakuContext.create();

        mDanmakuContext
                .setDanmakuStyle(IDisplayer.DANMAKU_STYLE_NONE)
                .setDuplicateMergingEnabled(false)
                //越大速度越慢
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.0f)
                .setCacheStuffer(new BackgroundCacheStuffer(), mCacheStufferAdapter)
                .setMaximumLines(maxLinesPair)
                .setMaximumVisibleSizeInScreen(0)
                .preventOverlapping(overlappingEnablePair);
    }


    /**
     * 绘制背景(自定义弹幕样式)
     * 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
     */
    private class BackgroundCacheStuffer extends SpannedCacheStuffer {
        final Paint paint = new Paint();

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
            super.measure(danmaku, paint, fromWorkerThread);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            paint.setAntiAlias(true);
            //黑色 普通
            IDanmakuData data = (IDanmakuData) danmaku.tag;
            paint.setColor(data.getBackgroundColor());
            RectF rectF = new RectF(left + DANMU_PADDING_INNER,
                    top + DANMU_PADDING_INNER,
                    left + danmaku.paintWidth,
                    top + danmaku.paintHeight - DANMU_PADDING_INNER);
            canvas.drawRoundRect(rectF, DANMU_RADIUS, DANMU_RADIUS, paint);
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }
    }

    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
//            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
//            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            if (danmaku.text instanceof Spanned) {
                danmaku.text = "";
            }
        }
    };

    public void setDanmakuView(IDanmakuView danmakuView) {
        this.mDanmakuView = danmakuView;
        initDanmuView();
    }

    private void initDanmuView() {
        if (mDanmakuView != null) {
            mDanmakuView.setCallback(new DrawHandler.Callback() {
                @Override
                public void prepared() {
                    mDanmakuView.start();
                }

                @Override
                public void updateTimer(DanmakuTimer timer) {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {

                }

                @Override
                public void drawingFinished() {

                }
            });
        }
        mDanmakuView.prepare(new BaseDanmakuParser() {

            @Override
            protected Danmakus parse() {
                return new Danmakus();
            }
        }, mDanmakuContext);
        mDanmakuView.enableDanmakuDrawingCache(true);
    }

    public void pause() {
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    public void hide() {
        if (mDanmakuView != null) {
            mDanmakuView.hide();
        }
    }

    public void show() {
        if (mDanmakuView != null) {
            mDanmakuView.show();
        }
    }

    public void resume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    public void destroy() {
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    public void clean() {
        if (mDanmakuView != null) {
            mDanmakuView.clearDanmakusOnScreen();
        }
    }

    public void addDanmu(final IDanmakuData danmu) {
        final BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.tag = danmu;
        final SpannableStringBuilder[] spannable = new SpannableStringBuilder[1];
        ImageLoaderHelper.loadImage(danmu.getUserIconUrl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, String failReason) {
                System.out.println(failReason);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try {
                    Drawable drawable = ImageLoaderHelper.createCircleDrawable(loadedImage);
                    drawable.setBounds(0, 0, BITMAP_WIDTH, BITMAP_HEIGHT);
                    spannable[0] = createSpannable(drawable, danmu);
                    danmaku.text = spannable[0];

                    danmaku.padding = DANMU_PADDING;
                    // 1:一定会显示, 一般用于本机发送的弹幕,但会导致行数的限制失效
                    danmaku.priority = 0;
                    danmaku.isLive = false;
                    danmaku.textSize = DANMU_TEXT_SIZE;
                    danmaku.textColor = Color.WHITE;
                    danmaku.textShadowColor = 0;
                    if (mDanmakuView != null) {
                        danmaku.setTime(mDanmakuView.getCurrentTime() + ADD_DANMU_TIME);
                        if (mLongDuration == null) {
                            mLongDuration = new Duration(DURATION_LONG);
                            mShortDuration = new Duration(DURATION_SHORT);
                        }
                        if (mDanmakuView.getCurrentVisibleDanmakus().size() >= MAX_SIZE) {
                            danmaku.setDuration(mShortDuration);
                        } else {
                            danmaku.setDuration(mLongDuration);
                        }
                        lastDanmuTime = danmaku.getTime();
                        mDanmakuView.addDanmaku(danmaku);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }


    private SpannableStringBuilder createSpannable(Drawable drawable, IDanmakuData danmu) {
        String text = "bitmap";
        String content = danmu.getContent();
        String nickName = danmu.getNickName();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        CenteredImageSpan span = new CenteredImageSpan(drawable, BITMAP_HEIGHT);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (!TextUtils.isEmpty(content)) {
            spannableStringBuilder.append(" ");
            SpannableString spannableNickName = new SpannableString(nickName);
            spannableNickName.setSpan(new ForegroundColorSpan(Color.parseColor("#fff7c0")), 0, spannableNickName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(spannableNickName);
            spannableStringBuilder.append(" ");
            spannableStringBuilder.append(danmu.getContent());
        }
        return spannableStringBuilder;
    }

    public void addDanmuWithoutIcon(final IDanmakuData danmu) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.tag = danmu;
        danmaku.text = danmu.getContent();
        danmaku.priority = 0;
        danmaku.isLive = true;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = UIUtils.dip2px(18);
        if (mLongDuration == null) {
            mLongDuration = new Duration(DURATION_LONG);
            mShortDuration = new Duration(DURATION_SHORT);
        }
        if(mColorRandom == null){
            mColorRandom = new Random();
        }
        danmaku.textColor = mColors[mColorRandom.nextInt(mColors.length)];
        if(mDanmakuView.getCurrentVisibleDanmakus().size() > 4){
            danmaku.setDuration(mShortDuration);
        }else {
            danmaku.setDuration(mLongDuration);
        }
        mDanmakuView.addDanmaku(danmaku);
    }
}
