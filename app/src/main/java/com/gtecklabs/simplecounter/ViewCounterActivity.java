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
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.ui.CountValueFormatter;

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
    CountValueTextWatcher.attach(mValueText);
  }

  @OnClick(R.id.increment)
  void onIncrementClicked() {
    getPresenter().onIncrementClicked();
  }

  @OnClick(R.id.decrement)
  void onDecrementClicked() {
    getPresenter().onDecrementClicked();
  }

  void bind(Count count) {
    mCollapsingToolbar.setTitle(count.title());
    mValueText.setText(CountValueFormatter.formatValue(count.value()));

    if (TextUtils.isEmpty(count.description())) {
      mDescription.setVisibility(View.GONE);
    } else {
      mDescription.setVisibility(View.VISIBLE);
      mDescription.setText(count.description());
    }

    setToolbarColor(count.color());
  }

  private void setToolbarColor(@ColorInt int color) {
    mToolbarBackgroundContent.setBackgroundColor(color);
    mCollapsingToolbar.setContentScrimColor(color);
    getWindow().setStatusBarColor(color);
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

  static class CountValueTextWatcher implements TextWatcher {

    static CountValueTextWatcher attach(EditText editText) {
      CountValueTextWatcher textWatcher = new CountValueTextWatcher(editText);
      editText.addTextChangedListener(textWatcher);
      return textWatcher;
    }

    private final EditText mEditText;

    private CountValueTextWatcher(EditText mEditText) {
      this.mEditText = mEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      // No-op
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      // No-op
    }

    @Override
    public void afterTextChanged(Editable editable) {
      if (TextUtils.isEmpty(editable)) {
        mEditText.removeTextChangedListener(this);
        mEditText.setText("0");
        mEditText.setSelection(1);
        mEditText.addTextChangedListener(this);
      }
    }
  }
}
