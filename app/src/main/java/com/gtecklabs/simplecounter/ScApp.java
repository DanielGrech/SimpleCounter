package com.gtecklabs.simplecounter;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.VisibleForTesting;
import butterknife.ButterKnife;
import com.facebook.stetho.Stetho;
import com.gtecklabs.simplecounter.di.AndroidModule;
import com.gtecklabs.simplecounter.di.DaggerDiComponent;
import com.gtecklabs.simplecounter.di.DiComponent;
import com.gtecklabs.simplecounter.util.StethoUtil;
import timber.log.Timber;

public class ScApp extends Application {

  private DiComponent mDiComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      enableDebugTools();
    }

     mDiComponent = DaggerDiComponent.builder()
         .androidModule(getAndroidModule())
         .build();
  }

  AndroidModule getAndroidModule() {
    return new AndroidModule(this);
  }

  @VisibleForTesting
  void enableDebugTools() {
    Timber.plant(new Timber.DebugTree());
    ButterKnife.setDebug(true);

    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .penaltyDeath()
        .build());
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .penaltyDeath()
        .build());

    StethoUtil.initialize(this);
  }

  public static DiComponent getDi(Context context) {
    final ScApp app = (ScApp) context.getApplicationContext();
    return app.mDiComponent;
  }
}
