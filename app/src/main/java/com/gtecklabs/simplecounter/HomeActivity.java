package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;

public class HomeActivity extends BaseActivity<HomeActivity, HomePresenter> {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

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
    setupToolbar();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @OnClick(R.id.fab)
  void onFabClicked() {
    getPresenter().onFabClicked();
  }

  private void setupToolbar() {
    mToolbar.setTitle(R.string.app_name);

    mToolbar.inflateMenu(R.menu.act_home);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
          // TODO: Open settings
          return true;
        }
        return false;
      }
    });
  }
}
