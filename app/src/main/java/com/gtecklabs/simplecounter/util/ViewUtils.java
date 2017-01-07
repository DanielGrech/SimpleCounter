package com.gtecklabs.simplecounter.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class ViewUtils {

  private ViewUtils() {
    // No instances..
  }

  public static void onPreDraw(final View view, final Runnable action) {
    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        ViewTreeObserver vto = view.getViewTreeObserver();
        if (vto.isAlive()) {
          vto.removeOnPreDrawListener(this);
          action.run();
        }
        return true;
      }
    });
  }

  public static Rect getPositionOnScreen(View view) {
    int[] coords = new int[2];
    view.getLocationOnScreen(coords);
    return new Rect(coords[0], coords[1], coords[0] + view.getWidth(), coords[1] + view.getHeight());
  }
}
