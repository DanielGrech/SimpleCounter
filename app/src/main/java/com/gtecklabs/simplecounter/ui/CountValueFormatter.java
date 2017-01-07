package com.gtecklabs.simplecounter.ui;

import java.util.Locale;

public class CountValueFormatter {

  public static String formatValue(float value) {
    if (isWholeNumber(value)) {
      return String.format(Locale.getDefault(), "%.0f", value);
    } else {
      return String.format(Locale.getDefault(), "%.2f", value);
    }
  }

  private static boolean isWholeNumber(float value) {
    return value % 1f == 0;
  }
}
