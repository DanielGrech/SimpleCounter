package com.gtecklabs.simplecounter.di;

import com.gtecklabs.simplecounter.NewCounterPresenter;
import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(NewCounterPresenter presenter);
}
