package com.gtecklabs.simplecounter;

import android.app.Activity;
import android.content.Intent;

public class Navigator {

  private final Activity mActivity;

  public Navigator(Activity mActivity) {
    this.mActivity = mActivity;
  }

  public void goToNewCounterScreen() {
    mActivity.startActivity(new Intent(mActivity, NewCounterActivity.class));
  }
}
