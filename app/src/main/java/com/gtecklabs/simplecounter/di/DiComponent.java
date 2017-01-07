package com.gtecklabs.simplecounter.di;

import com.gtecklabs.simplecounter.HomeActivity;
import com.gtecklabs.simplecounter.NewCounterActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component to provide dependency injection
 */
@Singleton
@Component(modules = {
    AndroidModule.class,
    DataModule.class,
})
public interface DiComponent {

  void inject(HomeActivity activity);

  void inject(NewCounterActivity activity);

  ActivityComponent newActivityComponent(ActivityModule module);
}
