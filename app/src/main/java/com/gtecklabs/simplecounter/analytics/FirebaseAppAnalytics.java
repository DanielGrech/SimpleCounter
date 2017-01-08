package com.gtecklabs.simplecounter.analytics;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAppAnalytics implements AppAnalytics {

  private final FirebaseAnalytics mFirebaseAnalytics;

  public FirebaseAppAnalytics(Context context) {
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
  }

  @Override
  public void logEvent(String event) {
    final Bundle analyticsBundle = new Bundle();
    analyticsBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, event);
    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, analyticsBundle);
  }
}
