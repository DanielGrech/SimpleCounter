package com.gtecklabs.simplecounter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.view.CountItemView;

public class Navigator {

  private final Activity mActivity;

  public Navigator(Activity mActivity) {
    this.mActivity = mActivity;
  }

  public void goToNewCounterScreen(@Nullable View sourceView) {
    startActivityFromView(NewCounterActivity.createIntentForNewCount(mActivity), sourceView);
  }

  public void goToEditCounterScreen(@Nullable View sourceView, Count count) {
    startActivityFromView(NewCounterActivity.createIntentForEdit(mActivity, count.id()), sourceView);
  }

  public void goToViewCountScreen(@Nullable CountItemView sourceView, Count count) {
    startActivityFromView(ViewCounterActivity.createIntent(mActivity, count.id()), sourceView);
  }

  private void startActivityFromView(Intent intent, @Nullable View sourceView) {
    if (sourceView == null) {
      startActivity(intent, null /* activityOptions */);
    } else {
      final int w = sourceView.getWidth();
      final int h = sourceView.getHeight();

      final ActivityOptions activityOptions;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activityOptions = ActivityOptions.makeClipRevealAnimation(sourceView, w / 2, h / 2, w, h);
      } else {
        activityOptions = ActivityOptions.makeScaleUpAnimation(sourceView, w / 2, h / 2, w, h);
      }

      startActivity(intent, activityOptions.toBundle());
    }
  }

  private void startActivity(Intent intent, @Nullable Bundle activityOptions) {
    if (BuildConfig.DEBUG) {
      // Force GC so that StrictMode works as intended
      System.gc();
    }

    if (activityOptions == null) {
      mActivity.startActivity(intent);
    } else {
      mActivity.startActivity(intent, activityOptions);
    }
  }
}
