package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;

public class NewCounterActivity extends BaseActivity<NewCounterActivity, NewCounterPresenter> {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

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

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
  }
}
