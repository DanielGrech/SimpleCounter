package com.gtecklabs.simplecounter;

import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.gtecklabs.simplecounter.data.CountLoader;
import com.gtecklabs.simplecounter.di.ActivityModule;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivityPresenter;
import com.gtecklabs.simplecounter.foundation.BaseSubscriber;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.util.Toaster;
import timber.log.Timber;

import javax.inject.Inject;

public class NewCounterPresenter extends BaseActivityPresenter<NewCounterActivity> {

  @Inject
  CountLoader mCountLoader;

  @Inject
  Toaster mToaster;

  public NewCounterPresenter(NewCounterActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.newActivityComponent(new ActivityModule(getActivity())).inject(this);
  }

  void onDoneClicked() {
    if (!validateUserInput()) {
      return;
    }

    final String title = getActivity().getTitleFromUserInput();
    final String description = getActivity().getDescriptionFromUserInput();
    final int color = getActivity().getColorFromUserInput();

    final Count newCountToSave = getCountFromUserInput(title, description, color);
    saveAndClose(newCountToSave);
  }

  @Override
  public boolean onBackPressed() {
    // TODO: Only allow going back if no changes made..
    return super.onBackPressed();
  }


  private boolean validateUserInput() {
    if (TextUtils.isEmpty(getActivity().getTitleFromUserInput())) {
      getActivity().showErrorForMissingName(getActivity().getString(R.string.error_new_counter_missing_name));
      return false;
    }

    return true;
  }

  private void saveAndClose(Count count) {
    subscribe(mCountLoader.saveCount(count), new BaseSubscriber<Count>() {
      @Override
      public void onError(Throwable e) {
        Timber.e(e, "Error saving new count");
        mToaster.toastShort(R.string.error_new_counter_saving);
      }

      @Override
      public void onNext(Count count) {
        Timber.d("%s saved!", count);
        mToaster.toastShort(R.string.new_count_success_messsage);
        getActivity().finish();
      }
    });
  }

  private static Count getCountFromUserInput(String title, @Nullable String description, @ColorInt int color) {
    return Count.builder()
        .title(title)
        .description(description)
        .color(color)
        .build();

  }
}
