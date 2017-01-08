package com.gtecklabs.simplecounter.util;

import android.view.View;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gtecklabs.simplecounter.prefs.AppPrefs;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class AdManager {

  private static final String TEST_DEVICE_NEXUS_6P = "8EE9FB218A7E597E00BEEB6B20C1B1C6";

  private static final int MIN_DAYS_BEFORE_SHOWING_ADS = 30;
  private static final int MIN_OPENS_BEFORE_SHOWING_ADS = 5;

  @Inject
  AppPrefs mAppPrefs;

  @Inject
  Clock mClock;

  @Inject
  AdManager() {

  }

  public void maybeShowAd(AdView adView) {
    if (shouldShowAds()) {
      adView.setVisibility(View.VISIBLE);
      adView.loadAd(new AdRequest.Builder().addTestDevice(TEST_DEVICE_NEXUS_6P).build());
    }
  }

  private boolean shouldShowAds() {
    final long numberOfTimesOpened = mAppPrefs.getAppOpenCount();
    final long timeOfFirstAppStart = mAppPrefs.getFirstAppOpenedTime();

    final long elapsedMs = mClock.now() - timeOfFirstAppStart;

    if (elapsedMs < TimeUnit.DAYS.toMillis(MIN_DAYS_BEFORE_SHOWING_ADS)) {
      // Hasn't had the app installed long enough
      return false;
    }

    if (numberOfTimesOpened < MIN_OPENS_BEFORE_SHOWING_ADS) {
      return false;
    }

    return true;
  }

}
