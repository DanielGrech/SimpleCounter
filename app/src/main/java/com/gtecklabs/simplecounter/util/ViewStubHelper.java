package com.gtecklabs.simplecounter.util;

import android.support.annotation.Nullable;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

public class ViewStubHelper<V extends View> {

  public interface Listener<T> {

    void onViewInflated(T view);
  }

  private final ViewStubCompat mViewStubCompat;

  private @Nullable V mView;

  private @Nullable Listener<V> mListener;

  public ViewStubHelper(ViewStubCompat viewStubCompat) {
    mViewStubCompat = Preconditions.checkNotNull(viewStubCompat);
  }

  public V getView() {
    if (mView == null) {
      // noinspection unchecked
      mView = (V) mViewStubCompat.inflate();
      if (mListener != null) {
        mListener.onViewInflated(mView);
      }
    }

    return mView;
  }

  public boolean isInflated() {
    return mView != null;
  }

  public void setListener(Listener<V> listener) {
    mListener = listener;
  }

  public void show() {
    V view = getView();
    view.setVisibility(View.VISIBLE);
  }

  public void hide() {
    if (isInflated()) {
      mView.setVisibility(View.GONE);
    }
  }

  public void hideInflated() {
    if (isInflated()) {
      mView.setVisibility(View.INVISIBLE);
    }
  }
}
