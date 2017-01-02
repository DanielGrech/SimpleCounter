package com.gtecklabs.simplecounter.util;

import android.app.Application;
import com.facebook.stetho.Stetho;

public final class StethoUtil {

  public static void initialize(Application app) {
    Stetho.initialize(Stetho.newInitializerBuilder(app)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(app))
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(app))
        .build());
  }
}
