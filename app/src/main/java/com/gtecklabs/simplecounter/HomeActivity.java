package com.gtecklabs.simplecounter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import butterknife.BindView;
import butterknife.OnClick;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.foundation.BaseActivity;
import com.gtecklabs.simplecounter.model.Count;
import com.gtecklabs.simplecounter.util.ViewUtils;
import com.gtecklabs.simplecounter.view.CountItemView;
import com.gtecklabs.simplecounter.view.CountListEmptyContainerView;

import java.util.List;

public class HomeActivity extends BaseActivity<HomeActivity, HomePresenter> {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.count_list)
  RecyclerView mCountRecyclerView;

  @BindView(R.id.error_view)
  View mErrorView;

  @BindView(R.id.empty_view)
  CountListEmptyContainerView mEmptyView;

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
    mCountRecyclerView.setVisibility(View.VISIBLE);
    mErrorView.setVisibility(View.GONE);
    mAdapter.bind(counts);
  }

  void showError() {
    mCountRecyclerView.setVisibility(View.GONE);
    mErrorView.setVisibility(View.VISIBLE);
  }

  void showEmptyView() {
    mEmptyView.setVisibility(View.VISIBLE);
    mCountRecyclerView.setVisibility(View.GONE);
    mErrorView.setVisibility(View.GONE);

    ViewUtils.onPreDraw(mEmptyView, new Runnable() {
      @Override
      public void run() {
        mEmptyView.postDelayed(new Runnable() {
          @Override
          public void run() {
            if (mEmptyView.isShown()) {
              mEmptyView.animateArrow();
            }
          }
        }, 500);
      }
    });
  }

  private void setupToolbar() {
    mToolbar.setTitle(R.string.app_name);

    mToolbar.inflateMenu(R.menu.act_home);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
          getPresenter().onSettingsClicked();
          return true;
        }
        return false;
      }
    });
  }

  private void setupRecyclerView() {
    mAdapter = new CountListAdapter();
    mAdapter.setListener(new CountListAdapter.Listener() {

      @Override
      public void onCountClicked(Count count) {
        getPresenter().onCountClicked(count);
      }

      @Override
      public void onIncrementClicked(Count count) {
        getPresenter().onIncrementCountClicked(count);
      }

      @Override
      public void onDecrementClicked(Count count) {
        getPresenter().onDecrementCountClicked(count);
      }
    });

    mCountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mCountRecyclerView.setAdapter(mAdapter);
    mCountRecyclerView.setHasFixedSize(true);
    mCountRecyclerView.addOnScrollListener(new ToolbarBackgroundScrollListener());
  }

  private class ToolbarBackgroundScrollListener extends RecyclerView.OnScrollListener {
    boolean mShouldDrawShadow;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      final boolean shouldDrawShadow = recyclerView.computeVerticalScrollOffset() > 0;
      if (mShouldDrawShadow != shouldDrawShadow) {
        mShouldDrawShadow = shouldDrawShadow;
        if (shouldDrawShadow) {
          mToolbar.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
          mToolbar.setElevation(getResources().getDimensionPixelSize(R.dimen.default_elevation));
          mToolbar.setBackgroundColor(Color.WHITE);
        } else {
          mToolbar.setOutlineProvider(null);
          mToolbar.setBackgroundColor(Color.TRANSPARENT);
        }
      }
    }
  }
}
