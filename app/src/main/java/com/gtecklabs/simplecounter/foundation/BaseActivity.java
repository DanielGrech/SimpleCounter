package com.gtecklabs.simplecounter.foundation;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.gtecklabs.simplecounter.ScApp;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.util.Preconditions;

public abstract class BaseActivity<A extends BaseActivity, P extends BaseActivityPresenter<A>>
    extends AppCompatActivity implements Presentable<P> {

  private Unbinder mViewUnbinder;
  private P mPresenter;

  protected abstract @LayoutRes int getLayoutResId();

  protected abstract void inject(DiComponent diComponent);

  protected abstract P createPresenter();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    inject(ScApp.getDi(this));
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());

    mViewUnbinder = ButterKnife.bind(this);

    mPresenter = Preconditions.checkNotNull(createPresenter());
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mPresenter.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    mPresenter.onPause();
    super.onPause();
  }

  @Override
  protected void onStop() {
    mPresenter.onStop();
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    mPresenter.onDestroy();

    if (mViewUnbinder != null) {
      mViewUnbinder.unbind();
    }
    mPresenter = null;
    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
    if (!getPresenter().onBackPressed()) {
      super.onBackPressed();
    }
  }

  @Override
  public final P getPresenter() {
    return mPresenter;
  }
}
