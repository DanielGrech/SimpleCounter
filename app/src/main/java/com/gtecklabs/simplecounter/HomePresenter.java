package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gtecklabs.simplecounter.data.CountLoader;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;
import com.gtecklabs.simplecounter.foundation.BaseSubscriber;
import com.gtecklabs.simplecounter.model.Count;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.List;

public class HomePresenter extends BaseActivityPresenter<HomeActivity> {

  @Inject
  CountLoader mCountLoader;

  public HomePresenter(HomeActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);
  }

  @Override
  public void onStart() {
    super.onStart();
    subscribe(
        mCountLoader.getAllCounts(),
        new BaseSubscriber<List<Count>>() {
          @Override
          public void onNext(List<Count> counts) {
            Timber.d("Reloaded all counts: %s", counts);
          }
        });
  }

  void onFabClicked() {
    getNavigator().goToNewCounterScreen();
  }
}
