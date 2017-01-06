package com.gtecklabs.simplecounter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.gtecklabs.simplecounter.R;

public class ColorPickerItemView extends View {

  private final Paint mPaint;

  private @Nullable Drawable mSelectedDrawable;

  public ColorPickerItemView(Context context, int color) {
    super(context);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    mPaint.setColor(color);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    final int w = canvas.getWidth();
    final int h = canvas.getHeight();

    final int rad = Math.max(w, h) / 2;
    canvas.drawCircle(w / 2, h / 2, rad, mPaint);

    if (isSelected()) {
      getSelectedDrawable().draw(canvas);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    maybeUpdateSelectedDrawablePadding();
  }

  @ColorInt
  public int getColor() {
    return mPaint.getColor();
  }

  private Drawable getSelectedDrawable() {
    if (mSelectedDrawable == null) {
      mSelectedDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_tick);
      maybeUpdateSelectedDrawablePadding();
    }

    return mSelectedDrawable;
  }

  private void maybeUpdateSelectedDrawablePadding() {
    if (mSelectedDrawable != null) {
      final int padding = getResources().getDimensionPixelSize(R.dimen.padding_small);
      mSelectedDrawable.setBounds(padding, padding, getWidth() - padding, getHeight() - padding);
    }
  }
}
