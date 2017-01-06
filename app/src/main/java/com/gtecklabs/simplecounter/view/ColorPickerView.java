package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import com.gtecklabs.simplecounter.R;

public class ColorPickerView extends GridLayout {

  private static final int[] COLORS = {
      0xFFF44336, 0xFF2196F3, 0xFF4CAF50, 0xFFFFC107, 0xFF673AB7, 0xFFFF5722, 0xFFF50057, 0xFF000000
  };

  private final int mRowCount;
  private final int mColumnCount;

  private final OnClickListener mOnColorClickedListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
        final View child = getChildAt(i);
        child.setSelected(child == view);
      }
    }
  };

  public ColorPickerView(Context context) {
    this(context, null);
  }

  public ColorPickerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    final int colors = COLORS.length;
    mRowCount = isInLandscape() ? 1 : 2;
    mColumnCount = (int) Math.ceil(colors / (1f * mRowCount));

    setRowCount(mRowCount);
    setColumnCount(mColumnCount);
    setOrientation(HORIZONTAL);
    setUseDefaultMargins(false);
    setupColors();
  }

  @ColorInt
  public int getSelectedColor() {
    for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
      final ColorPickerItemView view = (ColorPickerItemView) getChildAt(i);
      if (view.isSelected()) {
        return view.getColor();
      }
    }

    return Color.BLACK;
  }

  private void setupColors() {
    final int gridItemSize = getResources().getDimensionPixelSize(R.dimen.new_counter_color_picker_tile_size);
    final int gridMargins = getResources().getDimensionPixelOffset(R.dimen.padding_small);
    for (int row = 0; row < mRowCount; row++) {
      for (int col = 0; col < mColumnCount; col++) {
        final View view = new ColorPickerItemView(getContext(), COLORS[(mColumnCount * row) + col]);
        view.setOnClickListener(mOnColorClickedListener);
        view.setSelected(row == 0 && col == 0);

        final LayoutParams layoutParams = new LayoutParams(spec(row), spec(col));
        layoutParams.width = gridItemSize;
        layoutParams.height = gridItemSize;
        layoutParams.bottomMargin = 20;
        layoutParams.setMarginEnd(gridMargins);
        addView(view, layoutParams);
      }
    }
  }

  private boolean isInLandscape() {
    return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
  }
}
