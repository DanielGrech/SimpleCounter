package com.gtecklabs.simplecounter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

  static String EXTRA_EDIT_COUNT_ID = "count_id";

  private static final String STATE_KEY_NAME = "name";
  private static final String STATE_KEY_DESCRIPTION = "description";
  private static final String STATE_KEY_COLOR = "color";

  @Inject
  CountLoader mCountLoader;

  @Inject
  Toaster mToaster;

  private @Nullable Count mCountToEdit;

  private long mEditingCount = Count.NO_ID;

  boolean mHasRestoredInstanceState;

  public NewCounterPresenter(NewCounterActivity activity) {
    super(activity);
  }

  protected void inject(DiComponent component) {
    component.newActivityComponent(new ActivityModule(getActivity())).inject(this);
  }

  @Override
  public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mEditingCount = getActivity().getIntent().getLongExtra(EXTRA_EDIT_COUNT_ID, Count.NO_ID);
    if (mEditingCount != Count.NO_ID) {
      subscribe(
          mCountLoader.getCount(mEditingCount),
          new BaseSubscriber<Count>() {
            @Override
            public void onError(Throwable e) {
              mToaster.toastLong(R.string.error_loading_count);
              getActivity().finish();
            }

            @Override
            public void onNext(Count count) {
              mCountToEdit = count;
              if (!mHasRestoredInstanceState) {
                getActivity().setUserInputName(count.title());
                getActivity().setUserInputDescription(count.description());
                getActivity().setUserInputColor(count.color());
              }
            }
          });
    }
  }

  @Override
  public boolean onBackPressed() {
    final String nameFromUserInput = getActivity().getNameFromUserInput();
    final String descriptionFromUserInput = getActivity().getDescriptionFromUserInput();

    if (mCountToEdit == null) {
      if (!TextUtils.isEmpty(nameFromUserInput) || !TextUtils.isEmpty(descriptionFromUserInput)) {
        showConfirmExitDialog();
        return true;
      }
    } else {
      if (!TextUtils.equals(nameFromUserInput, mCountToEdit.title()) ||
          !TextUtils.equals(descriptionFromUserInput, mCountToEdit.description()) ||
          mCountToEdit.color() != getActivity().getColorFromUserInput()) {
        showConfirmExitDialog();
        return true;
      }
    }

    return super.onBackPressed();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(STATE_KEY_NAME, getActivity().getNameFromUserInput());
    outState.putString(STATE_KEY_DESCRIPTION, getActivity().getDescriptionFromUserInput());
    outState.putInt(STATE_KEY_COLOR, getActivity().getColorFromUserInput());

    mHasRestoredInstanceState = false;
  }

  @Override
  public void onRestoreInstanceState(Bundle state) {
    getActivity().setUserInputName(state.getString(STATE_KEY_NAME));
    getActivity().setUserInputDescription(state.getString(STATE_KEY_DESCRIPTION));
    getActivity().setUserInputColor(state.getInt(STATE_KEY_COLOR, Color.BLACK));

    mHasRestoredInstanceState = true;

    super.onRestoreInstanceState(state);
  }

  @Override
  public void onStart() {
    super.onStart();
    getActivity().showAd();
  }

  void onDoneClicked() {
    getAnalytics().logEvent("done");

    if (!validateUserInput()) {
      return;
    }

    final String title = getActivity().getNameFromUserInput();
    final String description = getActivity().getDescriptionFromUserInput();
    final int color = getActivity().getColorFromUserInput();

    final Count newCountToSave = getCountFromUserInput(title, description, color);
    saveAndClose(newCountToSave);
  }

  private void showConfirmExitDialog() {
    getAnalytics().logEvent("confirm_exit");

    new AlertDialog.Builder(getActivity())
        .setTitle(R.string.new_counter_confirm_exit_title)
        .setMessage(R.string.new_counter_confirm_exit_message)
        .setPositiveButton(R.string.new_counter_confirm_exit_confirmation, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            getActivity().finish();
          }
        })
        .setNegativeButton(R.string.new_counter_confirm_exit_cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int i) {
            dialog.dismiss();
          }
        })
        .show();
  }

  private boolean validateUserInput() {
    if (TextUtils.isEmpty(getActivity().getNameFromUserInput())) {
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

  private Count getCountFromUserInput(String title, @Nullable String description, @ColorInt int color) {
    final Count.Builder builder;
    if (mCountToEdit == null) {
      builder = Count.builder();
    } else {
      builder = mCountToEdit.toBuilder();
    }

    return builder.title(title).description(description).color(color).build();

  }
}
