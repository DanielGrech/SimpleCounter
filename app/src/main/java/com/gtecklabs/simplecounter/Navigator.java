package com.gtecklabs.simplecounter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

public class Navigator {

  private final Activity mActivity;

  public Navigator(Activity mActivity) {
    this.mActivity = mActivity;
  }

  public void goToNewCounterScreen(@Nullable View sourceView) {
    startActivityFromView(new Intent(mActivity, NewCounterActivity.class), sourceView);
  }

  private void startActivityFromView(Intent intent, @Nullable View sourceView) {
    if (sourceView == null) {
      mActivity.startActivity(intent);
    } else {
      final int w = sourceView.getWidth();
      final int h = sourceView.getHeight();

      final ActivityOptions activityOptions;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activityOptions = ActivityOptions.makeClipRevealAnimation(sourceView, w / 2, h / 2, w, h);
      } else {
        activityOptions = ActivityOptions.makeScaleUpAnimation(sourceView, w / 2, h / 2, w, h);
      }

      mActivity.startActivity(intent, activityOptions.toBundle());
    }
  }
}
