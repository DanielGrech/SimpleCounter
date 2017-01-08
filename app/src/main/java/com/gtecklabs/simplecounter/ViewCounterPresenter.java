package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gtecklabs.simplecounter.data.CountLoader;
import com.gtecklabs.simplecounter.di.ActivityModule;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;
import com.gtecklabs.simplecounter.foundation.BaseSubscriber;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.util.Preconditions;
import com.gtecklabs.simplecounter.util.Toaster;
import timber.log.Timber;

import javax.inject.Inject;

public class ViewCounterPresenter extends BaseActivityPresenter<ViewCounterActivity> {

  static final String EXTRA_COUNT_ID = "count_id";

  @Inject
  CountLoader mCountLoader;

  @Inject
  Toaster mToaster;

  private long mCountId;

  private
  @Nullable
  Count mCount;

  public ViewCounterPresenter(ViewCounterActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.newActivityComponent(new ActivityModule(getActivity())).inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);

    mCountId = getActivity().getIntent().getLongExtra(EXTRA_COUNT_ID, -1);
    Preconditions.checkArgument(mCountId >= 0, "No count id passed in intent");
  }

  @Override
  public void onStart() {
    super.onStart();
    subscribe(
        mCountLoader.getCount(mCountId),
        new BaseSubscriber<Count>() {
          @Override
          public void onError(Throwable e) {
            Timber.e(e, "Error loading count");
            mToaster.toastLong(R.string.error_loading_count);
            getActivity().finish();
          }

          @Override
          public void onNext(Count count) {
            mCount = count;
            getActivity().bind(count);
          }
        });

    getActivity().showAd();
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mCount != null) {
      final float userValue = getActivity().getUserValue();
      if (mCount.value() != userValue) {
        onUserChangedValue(userValue);
      }
    }
  }

  void onEditClicked() {
    if (mCount != null) {
      getNavigator().goToEditCounterScreen(mCount);
    }
  }

  void onDeleteClicked() {
    getAnalytics().logEvent("delete");

    subscribe(
        mCountLoader.deleteCount(mCountId),
        new BaseSubscriber<Void>() {
          @Override
          public void onError(Throwable e) {
            Timber.e(e, "Error deleting count");
            mToaster.toastShort(R.string.error_view_counter_delete);
          }

          @Override
          public void onNext(Void unused) {
            mToaster.toastShort(R.string.view_counter_success_message);
            mCount = null;
            getActivity().finish();
          }
        });
  }

  void onIncrementClicked() {
    if (mCount == null) {
      return;
    }

    getAnalytics().logEvent("increment");

    mCount = mCount.toBuilder().value(getActivity().getUserValue() + 1).build();
    subscribe(
        mCountLoader.saveCount(mCount),
        new BaseSubscriber<Count>() {
          @Override
          public void onError(Throwable e) {
            Timber.e(e, "Error incrementing count");
            mToaster.toastShort(R.string.error_incrementing_count);
          }
        });
  }

  void onDecrementClicked() {
    if (mCount == null) {
      return;
    }

    getAnalytics().logEvent("decrement");

    mCount = mCount.toBuilder().value(getActivity().getUserValue() - 1).build();
    subscribe(
        mCountLoader.saveCount(mCount),
        new BaseSubscriber<Count>() {
          @Override
          public void onError(Throwable e) {
            Timber.e(e, "Error decrementing count");
            mToaster.toastShort(R.string.error_decrementing_count);
          }
        });
  }

  public void onUserChangedValue(float value) {
    if (mCount == null) {
      return;
    }

    subscribe(
        mCountLoader.saveCount(mCount.toBuilder().value(value).build()),
        new BaseSubscriber<Count>() {
          @Override
          public void onNext(Count count) {
            // No-op. Value will reload automatically
          }

          @Override
          public void onError(Throwable e) {
            Timber.e(e, "Error changing user value");
          }
        });
  }
}
