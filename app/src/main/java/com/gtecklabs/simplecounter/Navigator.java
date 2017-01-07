package com.gtecklabs.simplecounter;

import android.app.Activity;
import android.content.Intent;
import com.gtecklabs.simplecounter.model.Count;

public class Navigator {

  private final Activity mActivity;

  public Navigator(Activity mActivity) {
    this.mActivity = mActivity;
  }

  public void goToAppSettingsScreen() {
    startActivity(AppPreferenceActivity.createIntent(mActivity));
  }

  public void goToNewCounterScreen() {
    startActivity(NewCounterActivity.createIntentForNewCount(mActivity));
  }

  public void goToEditCounterScreen(Count count) {
    startActivity(NewCounterActivity.createIntentForEdit(mActivity, count.id()));
  }

  public void goToViewCountScreen(Count count) {
    startActivity(ViewCounterActivity.createIntent(mActivity, count.id()));
  }

  private void startActivity(Intent intent) {
    if (BuildConfig.DEBUG) {
      // Force GC so that StrictMode works as intended
      System.gc();
    }

    mActivity.startActivity(intent);
  }
}
