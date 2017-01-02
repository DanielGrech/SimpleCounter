package com.gtecklabs.simplecounter.util;

import android.view.View;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;

public class InvalidatingSpringListener extends SimpleSpringListener {

  private final View mView;

  public InvalidatingSpringListener(View view) {
    this.mView = view;
  }

  @Override
  public void onSpringUpdate(Spring spring) {
    mView.invalidate();
  }
}
