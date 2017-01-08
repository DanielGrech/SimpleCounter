package com.gtecklabs.simplecounter.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import com.gtecklabs.simplecounter.ScApp;
import com.gtecklabs.simplecounter.di.annotations.ApiLevel;
import com.gtecklabs.simplecounter.di.annotations.ForApplicationContext;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AndroidModule {

  private final ScApp mApp;

  public AndroidModule(ScApp app) {
    this.mApp = app;
  }

  @Provides
  @Singleton
  ScApp providesApp() {
    return mApp;
  }

  @Provides
  @ForApplicationContext
  Context providesContext() {
    return mApp;
  }

  @Provides
  @Singleton
  SharedPreferences providesSharedPreferences(@ForApplicationContext Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Provides
  @Singleton
  WindowManager providesWindowManager(Context context) {
    return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
  }

  @Provides
  @Singleton
  @ApiLevel
  int providesApiLevel() {
    return Build.VERSION.SDK_INT;
  }
}
