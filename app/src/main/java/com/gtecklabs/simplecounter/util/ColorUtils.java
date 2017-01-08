package com.gtecklabs.simplecounter.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class ColorUtils {

  @ColorInt
  public static int darkenColor(@ColorInt int input) {
    float[] hsv = new float[3];
    Color.colorToHSV(input, hsv);
    hsv[2] *= 0.8f;
    return Color.HSVToColor(hsv);
  }
}
