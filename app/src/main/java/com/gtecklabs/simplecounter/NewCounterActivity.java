package com.gtecklabs.simplecounter;

import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;

public class NewCounterActivity extends BaseActivity<NewCounterActivity, NewCounterPresenter> {

  @Override
  protected int getLayoutResId() {
    return R.layout.act_new_counter;
  }

  @Override
  protected void inject(DiComponent diComponent) {
    diComponent.inject(this);
  }

  @Override
  protected NewCounterPresenter createPresenter() {
    return new NewCounterPresenter(this);
  }
}
