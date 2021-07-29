package com.humorusz.practice.liveroom.weidget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humorousz.commonutils.log.Logger;
import com.humorusz.practice.R;


public class ScoreProgressView extends FrameLayout {
  private SeekBar mSeekBar;
  private View mThumbView;

  public ScoreProgressView(@NonNull Context context) {
    this(context, null);
  }

  public ScoreProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public ScoreProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(context).inflate(R.layout.layout_score_progress_view, this);
    mSeekBar = findViewById(R.id.seek_bar);
    mThumbView = findViewById(R.id.thumb);
    mThumbView.setVisibility(GONE);
  }


  public void setProgress(int progress) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      mSeekBar.setProgress(progress, true);
    } else {
      mSeekBar.setProgress(progress);
    }
    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Logger.d("SeekBar", "current progress:" + progress);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }


  public int getProgress() {
    return mSeekBar.getProgress();
  }
}
