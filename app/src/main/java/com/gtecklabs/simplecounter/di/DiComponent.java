package com.gtecklabs.simplecounter.di;

import com.gtecklabs.simplecounter.*;
import dagger.Component;

import com.gtecklabs.simplecounter.HomeActivity;
import com.gtecklabs.simplecounter.HomePresenter;

import javax.inject.Singleton;

/**
 * Dagger component to provide dependency injection
 */
@Singleton
@Component(modules = {
    AndroidModule.class,
})
public interface DiComponent {

  void inject(HomeActivity activity);

  void inject(HomePresenter presenter);
}
