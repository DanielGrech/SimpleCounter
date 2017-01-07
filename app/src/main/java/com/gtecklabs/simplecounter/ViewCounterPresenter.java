package com.gtecklabs.simplecounter;

import com.gtecklabs.simplecounter.di.ActivityModule;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;

public class ViewCounterPresenter extends BaseActivityPresenter<ViewCounterActivity> {

  public ViewCounterPresenter(ViewCounterActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.newActivityComponent(new ActivityModule(getActivity())).inject(this);
  }
}
