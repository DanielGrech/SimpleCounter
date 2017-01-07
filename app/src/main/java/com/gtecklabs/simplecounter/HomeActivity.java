package com.gtecklabs.simplecounter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;
import com.gtecklabs.simplecounter.model.Count;

import java.util.List;

public class HomeActivity extends BaseActivity<HomeActivity, HomePresenter> {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.count_list)
  RecyclerView mCountRecyclerView;

  private CountListAdapter mAdapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.act_home;
  }

  @Override
  protected void inject(DiComponent diComponent) {
    diComponent.inject(this);
  }

  @Override
  protected HomePresenter createPresenter() {
    return new HomePresenter(this);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupToolbar();
    setupRecyclerView();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @OnClick(R.id.fab)
  void onFabClicked() {
    getPresenter().onFabClicked();
  }

  void bind(List<Count> counts) {
    mAdapter.bind(counts);
  }

  private void setupToolbar() {
    mToolbar.setTitle(R.string.app_name);

    mToolbar.inflateMenu(R.menu.act_home);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
          // TODO: Open settings
          return true;
        }
        return false;
      }
    });
  }

  private void setupRecyclerView() {
    mAdapter = new CountListAdapter();

    mCountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mCountRecyclerView.setAdapter(mAdapter);
    mCountRecyclerView.setHasFixedSize(true);
  }
}
