package com.gtecklabs.simplecounter.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public final class Toaster {

  private final Context mContext;

  public Toaster(Context context) {
    this.mContext = context;
  }

  public void toastShort(@StringRes int message) {
    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
  }

  public void toastLong(@StringRes int message) {
    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
  }
}
