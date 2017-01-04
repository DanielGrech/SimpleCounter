package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;
import com.gtecklabs.simplecounter.view.CustomToolbarView;

public class HomeActivity extends BaseActivity<HomeActivity, HomePresenter> {

  @BindView(R.id.toolbar)
  CustomToolbarView mToolbar;

  @Override
  protected int getLayoutResId() {
    return R.layout.act_home;
  }

  @Override
  protected void inject(DiComponent diComponent) {
    diComponent.inject(this);
  }

  @Override
  protected HomePresenter createPresenter() {
    return new HomePresenter(this);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void onBackPressed() {
    if (!getPresenter().onBackPressed()) {
      super.onBackPressed();
    }
  }

  @OnClick(R.id.fab)
  void onFabClicked() {
    getPresenter().onFabClicked();
  }

  void setToolbarTitle(String title) {
    mToolbar.setText(title);
  }
}
