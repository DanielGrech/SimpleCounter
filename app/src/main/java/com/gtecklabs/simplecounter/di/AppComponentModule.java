package com.gtecklabs.simplecounter.di;

import android.content.SharedPreferences;
import com.gtecklabs.simplecounter.prefs.AppPrefs;
import com.gtecklabs.simplecounter.util.Clock;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = {
    AndroidModule.class,
    UtilModule.class,
})
public class AppComponentModule {

  @Singleton
  @Provides
  AppPrefs providesAppPrefs(Clock clock, SharedPreferences sharedPreferences) {
    return new AppPrefs(clock, sharedPreferences);
  }
}

