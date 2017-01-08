package com.gtecklabs.simplecounter.analytics;

import timber.log.Timber;

public class LogcatAppAnalytics implements AppAnalytics {
  @Override
  public void logEvent(String event) {
    Timber.d("ANALYTICS: %s", event);
  }
}
