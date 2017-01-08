package com.gtecklabs.simplecounter.prefs;

import android.content.SharedPreferences;
import com.gtecklabs.simplecounter.util.Clock;

import javax.inject.Inject;

public class AppPrefs {

  private static final String KEY_FIRST_APP_OPEN = "first_app_open";
  private static final String KEY_LAST_APP_OPEN = "last_app_open";
  private static final String KEY_APP_OPEN_COUNT = "app_open_count";
  
  private final Clock mClock;
  private final SharedPreferences mSharedPrefs;

  @Inject
  public AppPrefs(Clock clock, SharedPreferences sharedPreferences) {
    mClock = clock;
    mSharedPrefs = sharedPreferences;
  }

  public void recordAppOpen() {
    SharedPreferences.Editor editor = mSharedPrefs.edit();
    if (!mSharedPrefs.contains(KEY_FIRST_APP_OPEN)) {
      editor.putLong(KEY_FIRST_APP_OPEN, mClock.now());
    }

    editor.putLong(KEY_LAST_APP_OPEN, mClock.now());
    editor.putInt(KEY_APP_OPEN_COUNT, getAppOpenCount() + 1);

    editor.apply();
  }

  public long getFirstAppOpenedTime() {
    return mSharedPrefs.getLong(KEY_FIRST_APP_OPEN, -1L);
  }

  public int getAppOpenCount() {
    return mSharedPrefs.getInt(KEY_APP_OPEN_COUNT, 0);
  }
}
