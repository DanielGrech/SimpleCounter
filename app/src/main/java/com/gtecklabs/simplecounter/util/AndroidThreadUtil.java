package com.gtecklabs.simplecounter.util;

import android.os.Handler;
import android.os.Looper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AndroidThreadUtil {

  private final Handler mUiHandler;

  @Inject
  public AndroidThreadUtil() {
    this.mUiHandler = new Handler(Looper.getMainLooper());
  }

  public void runOnUiThread(Runnable runnable) {
    mUiHandler.post(runnable);
  }

  public void runOnUiThread(Runnable runnable, long delay) {
    mUiHandler.postDelayed(runnable, delay);
  }

  public void cancel(Runnable runnable) {
    mUiHandler.removeCallbacks(runnable);
  }
}
