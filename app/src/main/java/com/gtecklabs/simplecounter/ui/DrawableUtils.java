package com.gtecklabs.simplecounter.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.util.TypedValue;

public class DrawableUtils {

  public static Drawable getDrawableFromTheme(Context context, @AttrRes int attrId) {
    final TypedValue typedValue = new TypedValue();
    if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
      return context.getDrawable(typedValue.resourceId);
    } else {
      throw new IllegalArgumentException("No attr " + attrId + " found in theme");
    }
  }
}
