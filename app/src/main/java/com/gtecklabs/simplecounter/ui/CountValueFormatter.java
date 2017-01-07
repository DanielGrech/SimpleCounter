package com.gtecklabs.simplecounter.ui;

import java.util.Locale;

public class CountValueFormatter {

  public static String formatValue(float value) {
    if (isWholeNumber(value)) {
      return String.format(Locale.getDefault(), "%.0f", value);
    } else {
      final String formatted = String.format(Locale.getDefault(), "%.2f", value);
      if (formatted.charAt(formatted.length() - 1) == '0') {
        return formatted.substring(0, formatted.length() - 1);
      } else {
        return formatted;
      }
    }
  }

  private static boolean isWholeNumber(float value) {
    return value % 1f == 0;
  }
}
