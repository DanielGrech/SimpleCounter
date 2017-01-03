package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;

public class HomePresenter extends BaseActivityPresenter<HomeActivity> {

  public HomePresenter(HomeActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);
    getActivity().setToolbarTitle(getActivity().getString(R.string.app_name));
  }

  public boolean onBackPressed() {
    return false;
  }
}
