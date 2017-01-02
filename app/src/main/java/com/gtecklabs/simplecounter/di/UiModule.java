package com.gtecklabs.simplecounter.di;

import com.facebook.rebound.SpringSystem;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UiModule {

  @Singleton
  @Provides
  SpringSystem providesSpringSystem() {
    return SpringSystem.create();
  }
}

