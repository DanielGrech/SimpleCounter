package com.gtecklabs.simplecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;
import com.gtecklabs.simplecounter.util.AdManager;
import com.gtecklabs.simplecounter.view.ColorPickerView;

import javax.inject.Inject;

public class NewCounterActivity extends BaseActivity<NewCounterActivity, NewCounterPresenter> {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.name)
  EditText mNameInput;

  @BindView(R.id.description)
  EditText mDescriptionInput;

  @BindView(R.id.color_picker)
  ColorPickerView mColorPicker;

  @BindView(R.id.ad_banner)
  AdView mAdView;

  @Inject
  AdManager mAdManager;

  public static Intent createIntentForNewCount(Context context) {
    return new Intent(context, NewCounterActivity.class);
  }

  public static Intent createIntentForEdit(Context context, long countId) {
    return new Intent(context, NewCounterActivity.class).putExtra(NewCounterPresenter.EXTRA_EDIT_COUNT_ID, countId);
  }

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
    setupToolbar();
  }

  private void setupToolbar() {
    mToolbar.setTitle(R.string.new_counter_screen_title);

    mToolbar.inflateMenu(R.menu.act_new_counter);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.done) {
          getPresenter().onDoneClicked();
          return true;
        }

        return false;
      }
    });

    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
  }

  @Nullable
  String getNameFromUserInput() {
    return mNameInput.getText() == null ? null : mNameInput.getText().toString();
  }

  @Nullable
  String getDescriptionFromUserInput() {
    return mDescriptionInput.getText() == null ? null : mDescriptionInput.getText().toString();
  }

  @ColorInt
  int getColorFromUserInput() {
    return mColorPicker.getSelectedColor();
  }

  void showErrorForMissingName(String errorMessage) {
    mNameInput.setError(errorMessage);
  }

  void setUserInputName(String name) {
    mNameInput.setText(name);
  }

  void setUserInputDescription(String description) {
    mDescriptionInput.setText(description);
  }

  void setUserInputColor(@ColorInt int color) {
    mColorPicker.setSelectedColor(color);
  }

  void showAd() {
    mAdManager.maybeShowAd(mAdView);
  }
}
