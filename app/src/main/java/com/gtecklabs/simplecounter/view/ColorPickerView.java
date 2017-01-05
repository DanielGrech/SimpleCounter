package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

public class ColorPickerView extends GridLayout {

  private static final int[][] COLORS = {
      new int[] {0xFFF44336, 0xFF2196F3, 0xFF4CAF50, 0xFFFFC107},
      new int[] {0xFF673AB7, 0xFFFF5722, 0xFFF50057, 0xFF000000},
  };

  public ColorPickerView(Context context) {
    this(context, null);
  }

  public ColorPickerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setRowCount(COLORS.length);
    setColumnCount(COLORS[0].length);
    setOrientation(HORIZONTAL);
    setupColors();
  }

  private void setupColors() {
    for (int row = 0; row < COLORS.length; row++) {
      for (int col = 0; col < COLORS[0].length; col++) {
        final View view = createColorView(COLORS[row][col]);

        final LayoutParams layoutParams = new LayoutParams(spec(row), spec(col));
        addView(view,layoutParams);
      }
    }
  }

  private View createColorView(@ColorInt int color) {
    View view = new View(getContext());
    view.setBackgroundColor(color);
    return view;
  }
}
