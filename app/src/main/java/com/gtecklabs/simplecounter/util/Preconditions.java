package com.gtecklabs.simplecounter.util;

public class Preconditions {

  public static <T> T checkNotNull(T object) {
    return checkNotNull(object, "");
  }

  public static <T> T checkNotNull(T object, String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }

    return object;
  }

  public static void checkState(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }

  public static void checkState(boolean condition, String template, Object... args) {
    if (!condition) {
      throw new IllegalStateException(String.format(template, args));
    }
  }

  public static void checkArgument(boolean condition, String message) {
    if (!condition) {
      throw new IllegalArgumentException(message);
    }
  }
}
