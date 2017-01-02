package com.gtecklabs.simplecounter.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class AppPrefs {
  
  private final SharedPreferences mSharedPrefs;

  @Inject
  public AppPrefs(SharedPreferences sharedPreferences) {
    mSharedPrefs = sharedPreferences;
  }
}
