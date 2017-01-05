package com.gtecklabs.simplecounter;

import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;

public class NewCounterPresenter extends BaseActivityPresenter<NewCounterActivity> {

  public NewCounterPresenter(NewCounterActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.inject(this);
  }

  void onDoneClicked() {
    // TODO: Save data and navigate home
  }

  @Override
  public boolean onBackPressed() {
    // TODO: Only allow going back if no changes made..
    return super.onBackPressed();
  }
}