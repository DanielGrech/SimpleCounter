package com.gtecklabs.simplecounter.di;

import com.gtecklabs.simplecounter.HomePresenter;
import com.gtecklabs.simplecounter.NewCounterPresenter;
import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(HomePresenter presenter);
  void inject(NewCounterPresenter presenter);
}
