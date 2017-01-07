package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.gtecklabs.simplecounter.data.CountLoader;
import com.gtecklabs.simplecounter.di.ActivityModule;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;
import com.gtecklabs.simplecounter.foundation.BaseSubscriber;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.util.Toaster;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.List;

public class HomePresenter extends BaseActivityPresenter<HomeActivity> {

  @Inject
  CountLoader mCountLoader;

  @Inject
  Toaster mToaster;

  public HomePresenter(HomeActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.newActivityComponent(new ActivityModule(getActivity())).inject(this);
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
            getActivity().bind(counts);
          }
        });
  }

  void onFabClicked(View fabView) {
    getNavigator().goToNewCounterScreen(fabView);
  }

  void onIncrementCountClicked(Count count) {
    subscribe(
        mCountLoader.saveCount(count.increment()),
        new BaseSubscriber<Count>() {
          @Override
          public void onError(Throwable e) {
            mToaster.toastShort(R.string.error_incrementing_count);
          }

          @Override
          public void onNext(Count count) {
            // No-op. List will reload automatically
          }
        });
  }

  void onDecrementCountClicked(Count count) {
    subscribe(
        mCountLoader.saveCount(count.decrement()),
        new BaseSubscriber<Count>() {
          @Override
          public void onError(Throwable e) {
            mToaster.toastShort(R.string.error_decrementing_count);
          }

          @Override
          public void onNext(Count count) {
            // No-op. List will reload automatically
          }
        });
  }
}
