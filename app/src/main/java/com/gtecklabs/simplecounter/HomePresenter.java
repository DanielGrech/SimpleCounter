package com.gtecklabs.simplecounter;

import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;

public class HomePresenter extends BaseActivityPresenter<HomeActivity> {

  public HomePresenter(HomeActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.inject(this);
  }

  public boolean onBackPressed() {
    return false;
  }
}
