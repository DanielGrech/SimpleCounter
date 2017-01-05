package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;

public class NewCounterActivity extends BaseActivity<NewCounterActivity, NewCounterPresenter> {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.name)
  EditText mNameInput;

  @BindView(R.id.description)
  EditText mDescriptionInput;

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
}
