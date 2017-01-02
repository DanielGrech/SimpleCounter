package com.gtecklabs.simplecounter.di;

import com.gtecklabs.simplecounter.util.Clock;
import com.gtecklabs.simplecounter.util.SystemClock;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.Random;

@Module
public class UtilModule {

  @Singleton
  @Provides
  Random providesRandom() {
    return new Random();
  }


  @Singleton
  @Provides
  Clock providesClock() {
    return new SystemClock();
  }
}
