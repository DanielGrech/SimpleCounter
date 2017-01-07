package com.gtecklabs.simplecounter.di;

import com.gtecklabs.simplecounter.HomeActivity;
import com.gtecklabs.simplecounter.NewCounterActivity;
import com.gtecklabs.simplecounter.ViewCounterActivity;
import com.gtecklabs.simplecounter.ui.SpringFactory;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component to provide dependency injection
 */
@Singleton
@Component(modules = {
    AndroidModule.class,
    DataModule.class,
    UiModule.class,
})
public interface DiComponent {

  void inject(HomeActivity activity);
  void inject(NewCounterActivity activity);
  void inject(ViewCounterActivity activity);

  ActivityComponent newActivityComponent(ActivityModule module);
  SpringFactory springFactory();
}
