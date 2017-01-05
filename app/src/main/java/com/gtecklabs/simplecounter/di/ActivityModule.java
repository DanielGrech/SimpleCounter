package com.gtecklabs.simplecounter.di;

import android.app.Activity;
import com.gtecklabs.simplecounter.util.Toaster;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

  private final Activity mActivity;

  public ActivityModule(Activity activity) {
    this.mActivity = activity;
  }

  @Provides
  public Toaster providesToaster() {
    return new Toaster(mActivity);
  }
}
