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

import javax.inject.Inject;

public class ViewCounterPresenter extends BaseActivityPresenter<ViewCounterActivity> {

  static final String EXTRA_COUNT_ID = "count_id";

  @Inject
  CountLoader mCountLoader;

  @Inject
  Toaster mToaster;

  private long mCountId;

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
    Preconditions.checkArgument(mCountId >=0 , "No count id passed in intent");
  }

  @Override
  public void onStart() {
    super.onStart();
    subscribe(
        mCountLoader.getCount(mCountId),
        new BaseSubscriber<Count>() {
          @Override
          public void onError(Throwable e) {
            mToaster.toastLong(R.string.error_loading_count);
            getActivity().finish();
          }

          @Override
          public void onNext(Count count) {
            getActivity().bind(count);
          }
        });
  }

  void onDeleteClicked() {
    subscribe(
        mCountLoader.deleteCount(mCountId),
        new BaseSubscriber<Void>() {
          @Override
          public void onError(Throwable e) {
            mToaster.toastShort(R.string.error_view_counter_delete);
          }

          @Override
          public void onNext(Void unused) {
            mToaster.toastShort(R.string.view_counter_success_message);
            getActivity().finish();
          }
        });
  }
}
