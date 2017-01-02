package com.gtecklabs.simplecounter.di;

import android.content.SharedPreferences;
import com.gtecklabs.simplecounter.prefs.AppPrefs;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = {
    AndroidModule.class,
})
public class AppComponentModule {

  @Singleton
  @Provides
  AppPrefs providesAppPrefs(SharedPreferences sharedPreferences) {
    return new AppPrefs(sharedPreferences);
  }
}

