package com.gtecklabs.simplecounter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.android.gms.ads.AdView;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.ui.CountValueFormatter;
import com.gtecklabs.simplecounter.util.AdManager;
import com.gtecklabs.simplecounter.util.ColorUtils;

import javax.inject.Inject;

public class ViewCounterActivity extends BaseActivity<ViewCounterActivity, ViewCounterPresenter> {

  @BindView(R.id.collapsing_toolbar)
  CollapsingToolbarLayout mCollapsingToolbar;

  @BindView(R.id.toolbar_background_content)
  View mToolbarBackgroundContent;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.description)
  TextView mDescription;

  @BindView(R.id.value)
  EditText mValueText;

  @BindView(R.id.ad_banner)
  AdView mAdView;

  @Inject
  AdManager mAdManager;

  public static Intent createIntent(Context context, long id) {
    return new Intent(context, ViewCounterActivity.class).putExtra(ViewCounterPresenter.EXTRA_COUNT_ID, id);
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.act_view_counter;
  }

  @Override
  protected void inject(DiComponent diComponent) {
    diComponent.inject(this);
  }

  @Override
  protected ViewCounterPresenter createPresenter() {
    return new ViewCounterPresenter(this);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupToolbar();
  }

  @OnClick(R.id.fab)
  void onEditFabClicked() {
    getPresenter().onEditClicked();
  }

  @OnClick(R.id.increment)
  void onIncrementClicked() {
    getPresenter().onIncrementClicked();
  }

  @OnClick(R.id.decrement)
  void onDecrementClicked() {
    getPresenter().onDecrementClicked();
  }

  float getUserValue() {
    final Editable text = mValueText.getText();
    return text == null || text.length() == 0 ?
        0f :
        Float.parseFloat(String.valueOf(text));
  }

  void bind(Count count) {
    mCollapsingToolbar.setTitle(count.title());

    final CharSequence newValueText = CountValueFormatter.formatValue(count.value());
    final CharSequence currentValueText = mValueText.getText();
    if (!TextUtils.equals(newValueText, currentValueText)) {
      mValueText.setText(newValueText);
    }

    if (TextUtils.isEmpty(count.description())) {
      mDescription.setVisibility(View.GONE);
    } else {
      mDescription.setVisibility(View.VISIBLE);
      mDescription.setText(count.description());
    }

    setToolbarColor(count.color());
  }

  void showAd() {
    mAdManager.maybeShowAd(mAdView);
  }

  private void setToolbarColor(@ColorInt int color) {
    mToolbarBackgroundContent.setBackgroundColor(color);
    mCollapsingToolbar.setContentScrimColor(color);
    getWindow().setStatusBarColor(ColorUtils.darkenColor(color));
  }

  private void setupToolbar() {
    mCollapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
    mCollapsingToolbar.setExpandedTitleColor(Color.WHITE);

    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });

    mToolbar.inflateMenu(R.menu.act_view_counter);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
          getPresenter().onDeleteClicked();
          return true;
        }

        return false;
      }
    });
  }
}
